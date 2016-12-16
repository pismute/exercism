import scala.collection.parallel.ForkJoinTaskSupport
import scala.concurrent.forkjoin.ForkJoinPool
import scala.collection.{GenSeq, GenMap}

object Frequency {
  def withForkJoin[A, B](n: Int, seq: GenSeq[A])(block: (GenSeq[A])=>B) = {
    val parSeq = seq.par

    parSeq.tasksupport = new ForkJoinTaskSupport(new ForkJoinPool(n))

    block(parSeq)
  }

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

  def countLetters(seq: GenSeq[String]) =
    seq.aggregate[GenMap[Char, Int]](Map.empty)(build, combine)

  def frequency(n: Int, seq: GenSeq[String]) =
    withForkJoin(n, seq)(countLetters)
}
