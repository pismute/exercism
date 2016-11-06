object Bearing extends Enumeration {
  type Bearing = Value
  val North, East, South, West = Value

  def nextRight(bearing: Bearing) =
    bearing match {
      case North => East
      case East => South
      case South => West
      case West => North
    }

  def nextLeft(bearing: Bearing) =
    bearing match {
      case North => West
      case East => North
      case South => East
      case West => South
    }
}

import Bearing._

case class Robot(bearing: Bearing, coordinates: (Int, Int)) {
  def turnLeft() = Robot(nextLeft(bearing), coordinates)
  def turnRight() = Robot(nextRight(bearing), coordinates)
  def advance = copy(coordinates = advanceCoordinates)
  def advanceCoordinates =
    bearing match {
      case North => (coordinates._1, coordinates._2 + 1)
      case East => (coordinates._1 + 1, coordinates._2)
      case South => (coordinates._1, coordinates._2 - 1)
      case West => (coordinates._1 - 1, coordinates._2)
    }

  def simulate(input: String) =
    input.foldLeft(this){
      case (robot, 'A') => robot.advance
      case (robot, 'L') => robot.turnLeft
      case (robot, 'R') => robot.turnRight
    }
}
