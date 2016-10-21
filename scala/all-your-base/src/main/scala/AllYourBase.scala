object AllYourBase {
  def unfoldLeft[A, B](seed: B)(f: B => Option[(B, A)]): Seq[A] = {
    def loop(seed: B)(ls: List[A]): List[A] =
      f(seed) match {
        case Some((b, a)) => loop(b)(a :: ls)
        case None => ls
      }

    loop(seed)(Nil)
  }

  def to10(base: Int, digits: Seq[Int]) =
    digits.reverse.zipWithIndex.foldLeft(0) {
      case (acc, (digit, i)) => digit * math.pow(base, i).toInt + acc
    }

  def from10(nr: Int, base: Int) =
    if(nr >= 0) Some(unfoldLeft(nr)(x => if(x>0) Some((x/base, x%base)) else None))
    else None

  def isValidDigit(base: Int, digit: Int) = 0 <= digit && digit < base

  def rebase(from: Int, digits: Seq[Int], to: Int) =
    if(from <= 1 || digits.exists(!isValidDigit(from, _: Int)) || to <= 1) None
    else from10(to10(from, digits), to)
}
