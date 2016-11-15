class Accumulate {
  def accumulate[A, B](f: A => B, m: Seq[A]): Seq[B] =
    m.foldRight(List.empty[B]){ (a, acc) =>
      f(a) :: acc
    }
}
