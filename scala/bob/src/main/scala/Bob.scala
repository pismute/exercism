// Fun Bob
class Bob {
  type Respond = PartialFunction[String, String]

  def silence:Respond = {case m if(m trim() isEmpty) => "Fine. Be that way!"}

  def shouting:Respond =
    {case m if(m.exists(_.isLetter) && !m.exists(_.isLower)) => "Whoa, chill out!"}

  val question:Respond ={case m if(m endsWith "?") => "Sure."}

  val whatever:Respond = {case _:String => "Whatever."}

  val hey:Respond =
    silence orElse
    shouting orElse
    question orElse
    whatever
}
