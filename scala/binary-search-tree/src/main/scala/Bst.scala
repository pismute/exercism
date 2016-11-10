case class Bst[+A](value: A, left: Option[Bst[A]] = None, right: Option[Bst[A]] = None) {
  def insert[B >: A: Ordering](x: B): Bst[B] =
    if(implicitly[Ordering[B]].lteq(x, value)) Bst(value, left.map(_.insert(x)).orElse(Some(Bst(x))), right)
    else Bst(value, left, right.map(_.insert(x)).orElse(Some(Bst(x))))

}

object Bst {
  def toList[A](xs: Bst[A]): List[A] = {
    def loop(xs:Bst[A], acc: List[A]): List[A] =
      xs match {
        case Bst(value, None, None) => value +: acc
        case Bst(value, Some(left), None) => loop(left, value +: acc)
        case Bst(value, None, Some(right)) => value +: loop(right, acc)
        case Bst(value, Some(left), Some(right)) => loop(left, value +: loop(right, acc))
      }

    loop(xs, Nil)
  }

  def fromList[A : Ordering](xs: Seq[A]): Bst[A] = {
    require(!xs.isEmpty)

    xs.foldLeft[Option[Bst[A]]](None){
      case (None, x) => Some(Bst(x))
      case (Some(root), x) => Some(root.insert(x))
    }
      .get
  }
}
