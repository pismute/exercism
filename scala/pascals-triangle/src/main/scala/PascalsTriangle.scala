object PascalsTriangle {
  val Triangle1 = List(1)
  val Triangle2 = List(1, 1)

  def triangleFrom2(n: Int) =
    if(n < 2) Nil
    else (2 until n)
      .scanLeft(Triangle2){(acc, _)=>
        1 +: acc.sliding(2)
          .map(_.sum)
          .foldRight(Triangle1)(_ +: _)
      }
      .toList

  def triangle(n: Int) =
    Triangle1 +: triangleFrom2(n)
}
