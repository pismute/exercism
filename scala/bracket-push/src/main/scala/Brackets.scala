import scala.annotation.tailrec

object Brackets {
  val BracketPairs = Map('}' -> '{', ')' -> '(', ']' -> '[')

  lazy val isOpen = BracketPairs.values.toSet
  lazy val isClose = BracketPairs.keySet

  def isValidClose(stack: List[Char], close: Char) =
    stack.headOption
      .flatMap(maybeOpen => BracketPairs.get(close).map(_ == maybeOpen))
      .getOrElse(false)

  def areBalanced(expression: String): Boolean = {
    def loop(start: Int, end: Int, stack: List[Char]): Option[List[Char]] =
      if (start == end) Some(stack)
      else {
        val char = expression(start)

        if(isOpen(char)) loop(start + 1, end, char +: stack)
        else if(isValidClose(stack, char)) loop(start + 1, end, stack.tail)
        else if(isClose(char)) None
        else loop(start + 1, end, stack)
      }

    loop(0, expression.length, Nil).map(_.isEmpty).getOrElse(false)
  }
}
