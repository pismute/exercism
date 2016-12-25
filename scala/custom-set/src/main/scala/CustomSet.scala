object CustomSet {
  val BucketSize = 64
  val EmptyBuckets = IndexedSeq.fill(BucketSize)(Seq.empty)
  val EmptySet = CustomSet(EmptyBuckets)
  def emptySet[A]: CustomSet[A] = EmptySet.asInstanceOf[CustomSet[A]]

  implicit class SeqOps[A](seq : Seq[A]) {
    def -(a : A) =
      seq.span(_ != a) match {
        case (xs, Seq(_, ys @ _*)) => xs ++ ys
      }
  }

  def apply[A](buckets: IndexedSeq[Seq[A]]): CustomSet[A] = new CustomSet(buckets)


  def fromList(as: Seq[Int]): CustomSet[Int] =
    as.foldLeft(emptySet[Int])(_ + _)

  def empty[A](set: CustomSet[A]): Boolean =
    set.isEmpty

  def member[A](set: CustomSet[A], a: A): Boolean =
    set(a)

  def isSubsetOf[A](set1: CustomSet[A], set2: CustomSet[A]): Boolean =
    set1.forall(a => set2(a))

  def isDisjointFrom[A](set1: CustomSet[A], set2: CustomSet[A]): Boolean =
    !set1.exists(a => set2(a))

  def isEqual[A](set1: CustomSet[A], set2: CustomSet[A]): Boolean =
    set1 == set2

  def union[A](set1: CustomSet[A], set2: CustomSet[A]): CustomSet[A] =
    set1 ++ set2

  def difference[A](set1: CustomSet[A], set2: CustomSet[A]): CustomSet[A] =
    set1 -- set2

  def insert[A](set: CustomSet[A], a: A): CustomSet[A] =
    set + a

  def intersection[A](set1: CustomSet[A], set2: CustomSet[A]): CustomSet[A] =
    set1 & set2
}

class CustomSet[A](buckets: IndexedSeq[Seq[A]]) extends (A => Boolean) {
  import CustomSet._

  def +(a: A): CustomSet[A] = {
    val hash = a.hashCode() % BucketSize
    val bucket = buckets(hash)

    bucket.find(_ == a)
      .map(_ => this)
      .getOrElse(CustomSet(buckets.updated(hash, a +: bucket)))
  }

  def -(a: A): CustomSet[A] = {
    val hash = a.hashCode() % BucketSize
    val bucket = buckets(hash)

    bucket.find(_ == a)
      .map(_ => CustomSet(buckets.updated(hash, bucket - a)))
      .getOrElse(this)
  }

  def ++(that: CustomSet[A]): CustomSet[A] =
    that.foldLeft(this)(_ + _)

  def --(that: CustomSet[A]): CustomSet[A] =
    that.foldLeft(this)(_ - _)

  def &(that: CustomSet[A]): CustomSet[A] =
    this.filter(that)

  def &~(that: CustomSet[A]): CustomSet[A] =
    (that -- this) ++ (this -- that)

  def applyOption(a: A): Option[A] = {
    val hash = a.hashCode() % BucketSize
    val bucket = buckets(hash)

    bucket.find(_ == a)
  }

  def isEmpty: Boolean =
    this.eq(emptySet)

  def apply(a: A): Boolean =
    applyOption(a).map(_ => true).getOrElse(false)

  def forall(p: A => Boolean): Boolean =
    buckets.flatten.forall(p)

  def exists(p: A => Boolean): Boolean =
    buckets.flatten.exists(p)

  def foldLeft[B](seed: B)(f: (B, A) => B): B =
    buckets.flatten.foldLeft(seed)(f)

  def filter(p: A => Boolean): CustomSet[A] =
    foldLeft(CustomSet.emptySet[A])((acc, a: A) => if(p(a)) acc + a else acc)

  override def toString: String =
    buckets.flatten.mkString("CustomSet(", ",", ")")

  def equalsPF: PartialFunction[Any, Boolean] = {
    case that: CustomSet[A] =>
      if(this.isEmpty && !that.isEmpty) false
      else forall(that)
    case _ => false
  }

  override def equals(that: Any): Boolean =
    equalsPF(that)

  override def hashCode: Int =
    super.hashCode + buckets.hashCode
}
