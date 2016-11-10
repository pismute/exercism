case class Bst[A: Ordering](value: A, left: Option[Bst[A]] = None, right: Option[Bst[A]] = None) {
  def insert(x: A): Bst[A] =
    if(implicitly[Ordering[A]].lteq(x, value)) copy(left = left.map(_.insert(x)).orElse(Some(Bst(x))))
    else copy(right = right.map(_.insert(x)).orElse(Some(Bst(x))))

  def foldRight[B](z: B)(f: (A, B) => B): B = {
    val rz = right.map(_.foldRight(z)(f)).getOrElse(z)
    val zz = f(value, rz)
    val lz = left.map(_.foldRight(zz)(f)).getOrElse(zz)
    lz
  }
}

object Bst {
  def toList[A](xs: Bst[A]): List[A] =
    xs.foldRight[List[A]](Nil)(_ +: _)

  def fromList[A : Ordering](xs: Seq[A]): Bst[A] = {
    require(!xs.isEmpty)

    xs.tail.foldLeft(Bst(xs.head))(_ insert _)
  }
}
