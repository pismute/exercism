object Bst {
  trait Bst[+A] {
    def isEmpty: Boolean
    def value: A
    def get = this // What does it work for?
    def left: Bst[A]
    def right: Bst[A]
    def insert[B >: A : Ordering](x: B): Bst[B]
  }

  case class BstNode[+A](value: A, left: Bst[A], right: Bst[A]) extends Bst[A] {
    def isEmpty: Boolean = false
    def insert[B >: A : Ordering](x: B): Bst[B] =
      if(implicitly[Ordering[B]].lteq(x, value)) BstNode(value, left.insert(x), right)
      else BstNode(value, left, right.insert(x))
  }

  case object Empty extends Bst[Nothing] {
    def isEmpty: Boolean = true
    def value = throw new NoSuchElementException("value of empty Bst")
    def left = throw new UnsupportedOperationException("left of empty Bst")
    def right = throw new UnsupportedOperationException("right of empty Bst")
    def insert[A: Ordering](value: A): Bst[A] = BstNode(value, Empty, Empty)
  }

  def toList[A](xs: Bst[A]) = {
    def loop(xs:Bst[A], acc: List[A]): List[A] =
      xs match {
        case Empty => acc
        case BstNode(value, left, right) => loop(left, value +: loop(right, acc))
      }

    loop(xs, Nil)
  }

  def fromList[A: Ordering](xs: Seq[A]) = xs.foldLeft[Bst[A]](Empty)(_ insert _)
  def apply[A: Ordering](value: A): Bst[A] = BstNode(value, Empty, Empty)
}
