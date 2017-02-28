import scala.annotation.tailrec

object Zipper {
  // from LYAHFGG
  trait Crumb[A] {
    def value: A
    def tree : Option[BinTree[A]]
  }

  case class LeftCrumb[A](value: A, tree: Option[BinTree[A]]) extends Crumb[A]
  case class RightCrumb[A](value: A, tree: Option[BinTree[A]]) extends Crumb[A]

  type BreadCrumbs[A] = Seq[Crumb[A]]

  type Zipper[A] = (BinTree[A], BreadCrumbs[A])

  // Get a zipper focussed on the root node.
  def fromTree[A](bt: BinTree[A]): Zipper[A] = (bt, Seq.empty)

  // Get the complete tree from a zipper.
  def toTree[A](zipper: Zipper[A]): BinTree[A] = {
    @tailrec
    def ups(z: Zipper[A]): BinTree[A] =
      up(z) match {
        case None => z._1
        case Some(z) => ups(z)
      }

    ups(zipper)
  }

  // Get the value of the focus node.
  def value[A](zipper: Zipper[A]): A = zipper._1.value

  // Get the left child of the focus node, if any.
  def left[A](zipper: Zipper[A]): Option[Zipper[A]] = {
    val (BinTree(value, left, right), xs) = zipper

    left.map(x => (x, LeftCrumb(value, right) +: xs))
  }

  // Get the right child of the focus node, if any.
  def right[A](zipper: Zipper[A]): Option[Zipper[A]] = {
    val (BinTree(value, left, right), xs) = zipper

    right.map(x => (x, RightCrumb(value, left) +: xs))
  }

  // Get the parent of the focus node, if any.
  def up[A](zipper: Zipper[A]): Option[Zipper[A]] =
    zipper match {
      case (_, Nil) => Option.empty
      case (t, Seq(LeftCrumb(value, tree), xs @ _*)) =>
        Option((BinTree(value, Option(t), tree), xs))
      case (t, Seq(RightCrumb(value, tree), xs @ _*)) =>
        Option((BinTree(value, tree, Option(t)), xs))
    }

  // Set the value of the focus node.
  def setValue[A](v: A, zipper: Zipper[A]): Zipper[A] = {
    val (t, xs) = zipper

    (t.copy(value = v), xs)
  }

  // Replace a left child tree.
  def setLeft[A](l: Option[BinTree[A]], zipper: Zipper[A]): Zipper[A] = {
    val (t, xs) = zipper

    (t.copy(left = l), xs)
  }


  // Replace a right child tree.
  def setRight[A](r: Option[BinTree[A]], zipper: Zipper[A]): Zipper[A] = {
    val (t, xs) = zipper

    (t.copy(right = r), xs)
  }
}

// A binary tree.
case class BinTree[A](value: A, left: Option[BinTree[A]], right: Option[BinTree[A]])
