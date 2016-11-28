import scala.annotation.tailrec

object PalindromeProducts {
  def apply(start: Int, limit: Int) = new PalindromeProducts(start, limit)
}

class PalindromeProducts(start: Int, limit: Int) {
  import PalindromeProducts._

  def pow10 = Math.pow(10, _:Int).toInt

  def isPalindrome(n: Int) = {
    @tailrec
    def reverse10(n: Int, acc:Int = 0): Int =
      if( n <= 0 ) acc
      else reverse10(n/10, acc*10 + n%10)

    n == reverse10(n)
  }

  lazy val products =
    (for{
      i <- (start to limit).toSet[Int]
      j <- i to limit
      if isPalindrome(i * j)
    } yield (i, j))
      .groupBy(x=> x._1 * x._2)

  lazy val smallest = products.minBy(_._1)

  lazy val largest = products.maxBy(_._1)
}
