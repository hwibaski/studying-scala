package _09

/*
  ArrayElement 는 Element에서 비공개가 아닌 멤버를 모두 물려받는다.

  1. 서브클래스는 슈퍼클래스의 비공개 멤버를 상속하지 않는다.
  2. 서브클래스에 슈퍼클래스와 같은 이름의 멤버가 있으면 슈퍼 클래스의 것을 상속받지 않는다.
 */
class ArrayElement(conts: Array[String]) extends Element {
  override def contents: Array[String] = conts
}
