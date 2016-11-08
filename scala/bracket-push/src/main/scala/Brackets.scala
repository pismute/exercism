import scala.annotation.tailrec

object Brackets {
  @tailrec
  private def foldl2[B](string: String)(start: Int, end: Int, z: B, op: (B, Char) => Option[B]): Option[B] =
    if (start == end) Some(z)
    else {
      val zz = op(z, string(start))
      if(zz.isEmpty) None
      else foldl2(string)(start + 1, end, zz.get, op)
    }

  def foldLeftOption[B](string: String)(z: B)(op: (B, Char) => Option[B]): Option[B] =
    foldl2(string)(0, string.length(), z, op)

  def areBalanced(expression: String): Boolean =
    foldLeftOption[List[Char]](expression)(Nil) {
      case (Nil, ']' | ')' | '}') => None
      case (acc, open @ ('[' | '(' | '{')) => Some(open +: acc)
      case (acc, ']') if acc.head == '[' => Some(acc.tail)
      case (acc, ']') => None
      case (acc, ')') if acc.head == '(' => Some(acc.tail)
      case (acc, ')') => None
      case (acc, '}') if acc.head == '{' => Some(acc.tail)
      case (acc, '}') => None
      case (acc, _) => Some(acc)
    }
        .fold(false)(_.isEmpty)
}
