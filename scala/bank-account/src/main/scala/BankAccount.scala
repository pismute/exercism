import java.util.concurrent.atomic.AtomicReference

object BankAccount {
  case class Delta(money:Int)

  def apply() = new BankAccount()
}

class BankAccount {
  import BankAccount._

  @volatile
  private var _balance = Option(0)

  def closeAccount() = _balance = None

  def incrementBalance(money: Int) = synchronized {
    _balance = _balance.map(_ + money)
    _balance
  }

  def getBalance = _balance
}
