object Sublist extends Enumeration {
  val Equal, Unequal, Sublist, Superlist = Value

  implicit class SeqHelpers(s: Seq[_]) {
    def isSublistOf(sub: Seq[_]) =
      if(sub.isEmpty) true
      else s.sliding(sub.size).exists(_ == sub)
  }

  def sublist(s: Seq[_], sub: Seq[_]) =
    if(s == sub) Equal
    else if(s isSublistOf sub) Superlist
    else if(sub isSublistOf s) Sublist
    else Unequal

}
