class DNA(nucleotides: String) {
  val Nucleotides = "ATCG".map((_, 0)).toMap

  require(nucleotides.forall(Nucleotides.contains))

  lazy val nucleotideCounts =
    Nucleotides ++
      nucleotides
        .groupBy(identity)
        .mapValues(_.size)

}
