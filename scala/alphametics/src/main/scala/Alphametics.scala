import scala.util.parsing.combinator._

object Alphametics {
  type Dict = Char => Int
  trait Value extends (()=> Option[Double]){
    def apply() : Option[Double]
  }

  case class Plus(left: Value, right: Value) extends Value {
    def apply() =
      for { l <- left(); r <- right() } yield l + r
  }

  case class Minus(left: Value, right: Value) extends Value {
    def apply() =
      for { l <- left(); r <- right() } yield l - r
  }

  case class Times(left: Value, right: Value) extends Value {
    def apply() =
      for { l <- left(); r <- right() } yield l * r
  }

  case class Div(left: Value, right: Value) extends Value {
    def apply() =
      for { l <- left(); r <- right() } yield l / r
  }

  case class Pow(left: Value, right: Double) extends Value {
    def apply() =
      for { l <- left() } yield math.pow(l, right)
  }

  case class Factor(alpha: String, dict: Dict) extends Value {
    def apply() = {
      val digits = alpha.map(dict)

      if(digits.head == 0) None
      else Option(digits.reduce((acc, d) => acc*10 + d))
    }
  }

  object LazyCalculator extends RegexParsers {
    def alphametricNumber: Parser[String] = """([A-Z]+)""".r
    def decimalNumber: Parser[String] = """(\d)""".r

    def factor: Parser[Dict => Value] =
      alphametricNumber ^^ ( nr => (dict) => Factor(nr, dict) )

    def decimalFactor: Parser[Double] = decimalNumber ^^ (_.toDouble)

    def term0: Parser[Dict => Value] =
      factor ~ ("^" ~ decimalFactor).? ^^ {
        case a ~ None => a
        case a ~ Some("^" ~ b) => (dict) => Pow(a(dict), b)
      }

    def term1: Parser[Dict => Value] =
      term0 ~ (("*" | "/") ~ term0).* ^^ {
        case init ~ terms =>
          (init /: terms) {
            case (acc, "*" ~ term) => (dict) => Times(acc(dict), term(dict))
            case (acc, "/" ~ term) => (dict) => Div(acc(dict), term(dict))
          }
      }

    def term2: Parser[Dict => Value] =
      term1 ~ (("+" | "-") ~ term1).* ^^ {
        case init ~ terms =>
          (init /: terms) {
            case (acc, "+" ~ term) => (dict) => Plus(acc(dict), term(dict))
            case (acc, "-" ~ term) => (dict) => Minus(acc(dict), term(dict))
          }
      }

    def term3: Parser[Dict => Option[Boolean]] =
      term2 ~ "==" ~ term2 ^^ {
        case a ~ "==" ~ b =>
          (dict) =>
            for{
              aa <- a(dict).apply()
              bb <- b(dict).apply()
            } yield aa == bb
      }

    def calculator(question: String): Dict => Option[Boolean] =
      parseAll(term3, question) match {
        case Success(answer, _) => answer
        case failure =>
          println(failure)
          (_)=> None
      }
  }

  def letters(exp: String) = exp.filter(_.isLetter).distinct

  def dictionaries(letters: String) : Stream[Dict] = {
    for {
      start <- (0 to (10 - letters.size)).toStream
      metric <- (start until 10).combinations(letters.size).flatMap(_.permutations)
    } yield (letters zip metric).toMap
  }

  def solve(expr: String) = {
    val dicts = dictionaries(letters(expr))

    val calculator = LazyCalculator.calculator(expr)

    dicts.find(dict => !calculator(dict).filter(_.self).isEmpty)
  }
}
