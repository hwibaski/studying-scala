package _09

import _09.ElementV2.elem

abstract class ElementV2 {
  def contents: Array[String]

  def width: Int = if (height == 0) 0 else contents(0).length

  def height: Int = contents.length

  def above(that: ElementV2): ElementV2 =
    elem(this.contents ++ that.contents)

  def beside(that: ElementV2): ElementV2 =
    elem(
      for (
        (line, line2) <- this.contents zip that.contents
      ) yield line + line2
    )

  def widen(w: Int): ElementV2 =
    if (w <= width) this
    else {
      val left = elem(' ', (w - width) / 2, height)
      val right = elem(' ', w - width - left.width, height)
      left beside this beside right
    }

  def heighten(h: Int): ElementV2 =
    if (h <= height) this
    else {
      val top = elem(' ', width, (h - height) / 2)
      val bot = elem(' ', width, h - height - top.height)
      top above this above bot
    }

  override def toString: String = contents mkString "\n"
}

object ElementV2 {
  private class ArrayElementV5(val contents: Array[String]) extends ElementV2

  private class LineElementV4(s: String) extends ElementV2 {
    val contents: Array[String] = Array(s)

    override def width: Int = s.length

    override def height = 1
  }

  private class UniformElementV3(ch: Char, override val width: Int, override val height: Int) extends ElementV2 {
    private val line = ch.toString * width

    def contents = Array.fill(height)(line)
  }


  def elem(contents: Array[String]): ElementV2 =
    new ArrayElementV5(contents)

  def elem(chr: Char, width: Int, height: Int): ElementV2 =
    new UniformElementV3(chr, width, height)

  def elem(line: String): ElementV2 =
    new LineElementV4(line)
}
