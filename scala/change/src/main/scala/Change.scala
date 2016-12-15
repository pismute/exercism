import scala.annotation.tailrec

object Change {
  def validMoney(money: Int, minCoin: Int): Option[Int] =
    if(money == 0) Option(money)
    else if(money < minCoin || money < 0) None
    else Option(money)

  def fewestCoins(money: Int, coins: Seq[Int]): Option[Seq[Int]] = {
    @tailrec
    def loop(tries: Seq[(Int, Seq[Int], Seq[Int])], // Seq((money, coins, changes))
        acc: Option[Seq[Int]]): Option[Seq[Int]] =
      tries match {
        case Seq() => acc
        case Seq((0, _, changes), xs @ _*) =>
          loop(xs, acc.filter(_.size < changes.size).orElse(Option(changes)))
        case Seq((_, Seq(), _), xs @ _*) => loop(xs, acc)
        case Seq((m, coins @ Seq(c, cs @ _*), changes), xs @ _*) if acc.forall(_.size > changes.size) =>
          val next = (m, cs, changes) +: xs
          val appended =
            if(m < c) next
            else (m-c, coins, c +: changes) +: next

          loop(appended, acc)
        case Seq(_, xs @ _*) => loop(xs, acc)
      }

    loop(Seq((money, coins, Seq())), None)
  }

  def findFewestCoins(money: Int, coins: Seq[Int]): Option[Seq[Int]] =
    for {
      m <- validMoney(money, coins.min)
      changes <- fewestCoins(money, coins.sortBy(-_))
    } yield changes

}
