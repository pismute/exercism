object Isogram {

  def isIsogram(xs: String): Boolean = {
    val ys =
      xs.toLowerCase()
        .filter(_.isLetter)

    ys.distinct.size == ys.size
  }
}
