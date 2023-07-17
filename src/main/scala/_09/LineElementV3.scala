package _09

class LineElementV3(s: String) extends ElementV2 {
  val contents: Array[String] = Array(s)

  override def width: Int = s.length

  override def height = 1
}
