object Strain {
  def keep[A](xs : Seq[A], f: A => Boolean) =
    (xs :\ Seq.empty[A]){ (x, ys) =>
      if( f(x) ) x +: ys else ys
    }

  val not: Boolean => Boolean = !_

  def discard[A](xs : Seq[A], f: A => Boolean) =
    keep(xs, not compose f)
}
