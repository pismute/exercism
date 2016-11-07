package

object Brackets {
  def areBalanced(expression: String) =
    expression
      .foldLeft(Option(List.empty[Char])) {
        case (Some(Nil), (']' | ')' | '}')) => None
        case (acc, open @ ('[' | '(' | '{')) => acc.map(open +: _)
        case (acc, ']') => acc.filter(_.head == '[').map(_.tail)
        case (acc, ')') => acc.filter(_.head == '(').map(_.tail)
        case (acc, '}') => acc.filter(_.head == '{').map(_.tail)
        case (acc, _) => acc
      }
        .fold(false)(_.isEmpty)

}
