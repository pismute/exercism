class Accumulate {
  def accumulate[A, B](f: A => B, m: Seq[A]): Seq[B] =
    // for(a <- m) yield f(a)
    m.foldRight(List.empty[B]){ (a, acc) =>
      f(a) :: acc
    }
}
