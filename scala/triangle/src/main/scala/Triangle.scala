object TriangleType extends Enumeration {
  val Equilateral, Isosceles, Scalene, Illogical = Value
}

object Triangle {
  def apply(a: Int, b: Int, c: Int) = new Triangle(a, b, c)
}

class Triangle (a:Int, b:Int, c: Int) {
  lazy val Seq(first, second, third) = Seq(a, b, c).sorted

  lazy val triangleType =
    if(first <= 0 || first + second < third) TriangleType.Illogical
    else if(first == second && second == third) TriangleType.Equilateral
    else if(second == third) TriangleType.Isosceles
    else TriangleType.Scalene
}
