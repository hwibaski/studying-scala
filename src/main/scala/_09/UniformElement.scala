package _09

/*
  Element를 상속하되 상속한 Element를 바로 재정의하면서 가능하다..
 */
class UniformElement(ch: Char, override val width: Int, override val height: Int) extends Element {
  private val line = ch.toString * width

  def contents = Array.fill(height)(line)
}

