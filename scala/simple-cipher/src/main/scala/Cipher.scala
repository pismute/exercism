import scala.util.Random

object Cipher {
  def apply(optKey: Option[String]) = new Cipher(optKey)
}

class Cipher(optKey: Option[String]) {
  import Cipher._

  for(k <- optKey) {
    require(!k.isEmpty)
    require(k.forall(_.isLower))
  }

  def randomKey(n: Int) = Option(Random.alphanumeric.filter(_.isLower).take(n).mkString)

  lazy val key: String = optKey.orElse(randomKey(26)).get

  def encode(plain: String) =
    mapWithKey(plain, { case (p, k) => (p + (k - 'a')).toChar })

  def decode(cipher: String) =
    mapWithKey(cipher, { case (p, k) => (p - (k - 'a')).toChar })

  def mapWithKey(text: String, f: ((Char, Char)) => Char) =
    (text zip key)
      .map(f)
      .map(normalize)
      .mkString

  def normalize(c: Char) =
    if(c.isLower) c
    else if( c < 'a') (c + 26).toChar
    else (c - 26).toChar
}
