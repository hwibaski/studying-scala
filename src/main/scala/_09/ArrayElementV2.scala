package _09


/*
  필드와 메서드가 같은 네임스페이스에 속한다 -> 필드가 파라미터 없는 메서드를 오버라이드 할 수 있다.
  Element의 contents 메서드를 필드로 구현
 */
class ArrayElementV2(conts: Array[String]) extends Element {
  val contents: Array[String] = conts
}
