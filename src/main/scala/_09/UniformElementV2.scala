package _09

class UniformElementV2(ch: Char, override val width: Int, override val height: Int) extends ElementV2 {
  private val line = ch.toString * width

  def contents = Array.fill(height)(line)
}

