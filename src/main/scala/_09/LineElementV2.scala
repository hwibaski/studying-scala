package _09

class LineElementV2(s: String) extends Element {
  val contents: Array[String] = Array(s)

  override def width: Int = s.length

  override def height = 1
}
