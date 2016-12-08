import scala.util._

object SecretHandshake {
  val Handshakes: Seq[(Int, Seq[String] => Seq[String])] =
    Seq((1 << 3, "jump" +: _),
       (1 << 2, "close your eyes" +: _),
       (1 << 1, "double blink" +: _),
       (1, "wink" +: _),
       (1 << 4, _.reverse))

  def handshake(nr: Int): Seq[String] =
    (Seq.empty[String] /: Handshakes){
      case (acc, (mask, f)) => if((nr & mask) == mask) f(acc) else acc
    }

  def handshake(binary: String): Seq[String] =
    Try(BigInt(binary, 2)) match {
      case Success(nr) => handshake(nr.toInt)
      case _ => Seq.empty
    }
}
