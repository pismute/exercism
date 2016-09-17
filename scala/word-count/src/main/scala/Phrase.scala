class Phrase(sentences: String) {
  def wordCount =
    sentences
      .toLowerCase
      .split("[^\\w']+")
        .groupBy(identity)
          .mapValues(_.size)
}
