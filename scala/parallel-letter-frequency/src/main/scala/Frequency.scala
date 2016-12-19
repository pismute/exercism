import scala.collection.parallel.ForkJoinTaskSupport
import scala.concurrent.forkjoin.ForkJoinPool
import scala.collection.{GenSeq, GenMap}
import java.util.concurrent.Executors
import scala.concurrent.{Future, ExecutionContext, Await}
import scala.concurrent.duration.Duration
import scala.annotation.tailrec

object Frequency {
  def combine(l: GenMap[Char, Int], r: GenMap[Char, Int]) =
    (l.keySet ++ r.keySet)
      .map( k => (k -> (l.getOrElse(k, 0) + r.getOrElse(k, 0)) ) )
      .toMap

  def build(acc: GenMap[Char, Int], text: String) = {
    val grouped =
      text
        .filter(_.isLetter)
        .toLowerCase
        .groupBy(identity)
          .mapValues(_.size)

    combine(acc, grouped)
  }

  def withForkJoin[A, B](n: Int, seq: GenSeq[A])(block: (GenSeq[A])=>B): B = {
    val parSeq = seq.par

    parSeq.tasksupport = new ForkJoinTaskSupport(new ForkJoinPool(n))

    block(parSeq)
  }

  def countLettersWithPar(seq: GenSeq[String]) =
    seq.aggregate[GenMap[Char, Int]](Map.empty)(build, combine)

  def withFuture[A, B](n: Int, seq: Seq[A])(build: (GenSeq[A]) => B, combine: (B, B) => B): B = {
    implicit val executionContext =
      ExecutionContext.fromExecutorService(Executors.newWorkStealingPool(n))

    val futures =
      seq.grouped(seq.size/n + 1)
        .map(xs => Future(build(xs)))

    Await.result(Future.reduce(futures)(combine), Duration.Inf)
  }

  def countLettersWithFuture(seq: GenSeq[String]) =
    seq.foldLeft[GenMap[Char, Int]](Map.empty)(build)

  def frequency(n: Int, seq: Seq[String]): GenMap[Char, Int] =
    if(seq.isEmpty) GenMap.empty
    //else withForkJoin(n, seq)(countLettersWithPar)
    else withFuture(n, seq)(countLettersWithFuture, combine)
}
