import scala.util.parsing.combinator._

object WordProblem {
  object WordyCalculator extends JavaTokenParsers {
    def factor: Parser[Double] = floatingPointNumber ^^ (_.toDouble)

    def operator: Parser[(Double, Double) => Double] =
      ("plus" | "minus" | "multiplied by" | "divided by" | "raised to the") ^^ {
        case "plus" => (a, b) => a + b
        case "minus" => (a, b) => a - b
        case "multiplied by" => (a, b) => a * b
        case "divided by" => (a, b) => a / b
        case "raised to the" => (a, b) => math.pow(a, b)
      }

    def term: Parser[Double] =
      factor ~ (operator ~ factor).* ^^ {
        case init ~ terms =>
          (init /: terms){ case (acc, op ~ operand)=> op(acc, operand) }
      }

    def expr: Parser[Double] = term

    def problem: Parser[Int] = "What is" ~> expr <~ "?" ^^ (_.toInt)

    def answer(question: String) =
      parseAll(problem, question) match {
        case Success(answer, _) => Option(answer)
        case _ => None
      }
  }

  def apply(question: String) = WordyCalculator.answer(question)
}
