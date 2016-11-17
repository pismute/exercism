object Minesweeper {
  val Neighbours =
    for {
      x <- -1 to 1
      y <- -1 to 1 if !(x == 0 && y == 0)
    } yield (x, y)

  def annotate(input: Seq[String]): Seq[String] =
    if( input.isEmpty) input
    else {
      val board = input.map(_.toIndexedSeq).toIndexedSeq
      val width = board.head.size;
      val height = board.size;

      def isOnBoard(x: Int, y: Int): Boolean = 0 <= x && x < width && 0 <= y && y < height
      def isMine(x: Int, y: Int): Boolean = board(y)(x) == '*'

      def findMine(x: Int, y: Int): Char =
        if(isMine(x, y)) board(y)(x)
        else {
          val neighbors =
            Neighbours
              .map{ case(xx, yy) => (xx + x, yy + y) }
              .filter{ case(xx, yy) => isOnBoard(xx, yy) }
              .filter{ case(xx, yy) => isMine(xx,yy) }

          (neighbors.size + '0').toChar
        }

      (for {
        x <- 0 until width
        y <- 0 until height
      } yield findMine(x, y))
        .map(char=> if(char == '0') ' ' else char)
        .grouped(width)
        .map(_.mkString)
        .toSeq
    }
}
