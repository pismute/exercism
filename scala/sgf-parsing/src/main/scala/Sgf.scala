import scala.Function.const
import scala.util.parsing.combinator.RegexParsers

object Sgf extends RegexParsers {
  val Properties = Set("FF", "C", "SZ", "B", "AB", "W", "AW", "A", "C")

  type Tree[A] = Node[A] // to separate the type from the constructor, cf. Haskell's Data.Tree
  type Forest[A] = List[Tree[A]]
  case class Node[A](rootLabel: A, subForest: Forest[A] = List())

  // A tree of nodes.
  type SgfTree = Tree[SgfNode]

  // A node is a property list, each key can only occur once.
  // Keys may have multiple values associated with them.
  type SgfNode = Map[String, List[String]]

  def parseSgf(text: String): Option[SgfTree] =
    parseAll(sgf, text) match {
      case Success(builder, _) => builder(Properties, List.empty).map(_.head)
      case failed =>
        //println(failed)
        None
    }

  ///Parser
  type Dict = String => Boolean
  type SgfBuilder = (Dict, Forest[SgfNode]) => Option[Forest[SgfNode]]

  override val skipWhitespace = false

  def valueLiteral: Parser[String] = {
    val escapedNewline: Parser[String] = """\\\s""".r ^^ const("")
    val whitespace: Parser[String] = """\s""".r ^^ const(" ")
    val escapedChar: Parser[String] = """\\.""".r ^^ (_.drop(1))
    val notCloseSquare: Parser[String] = "[^]]".r

    escapedNewline | escapedChar | whitespace | notCloseSquare
  }

  def key: Parser[String] = "\\w+".r
  def value: Parser[List[String]] =
    ("[" ~> (valueLiteral).+ <~ "]").+ ^^ (_.map(_.mkString))

  def node: Parser[SgfBuilder] =
    ";" ~> (key ~ value).? ^^ {
      case Some(k ~ vs) =>
        (dict, forest) =>
          if(!dict(k)) None
          else Option(List(Node(Map(k -> vs), forest)))
      case None =>
        (dict, forest) => Option(List(Node(Map())))
    }

  def nodes: Parser[SgfBuilder] =
    (node).+ ^^ { nodes =>
      (dict, forest) =>
        nodes.foldRight(Option(forest)) { (builder, acc) =>
          acc.flatMap(trees => builder(dict, trees))
        }
    }

  def sgf: Parser[SgfBuilder] =
    "(" ~> nodes ~ (sgf).* <~ ")" ^^ {
      case nodes ~ Nil =>
        (dict, forest) => nodes(dict, List.empty)
      case nodes ~ sgfs =>
        (dict, forest) =>
          nodes(dict,
            sgfs.foldRight(Option(forest)){ (builder, acc) =>
              for {
                ys <- acc
                zs <- builder(dict, List.empty)
              } yield zs ++ ys
            }.get)
    }
}
