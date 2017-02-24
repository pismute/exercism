import scala.annotation.tailrec

object BinarySearch {

  def search[A : Ordering](seq: IndexedSeq[A], a: A): Option[Int] = {
    @tailrec
    def loop(start: Int, limit: Int): Option[Int] = {
      if( start >= limit ) None
      else {
        val size = limit - start
        val middle = start + size / 2
        val pivot = seq(middle)

        if( implicitly[Ordering[A]].equiv(pivot, a) ) Option(middle)
        else if( implicitly[Ordering[A]].lt(pivot, a) ) loop(middle + 1, limit)
        else loop(start, middle)
      }
    }

    loop(0, seq.size)
  }

}
