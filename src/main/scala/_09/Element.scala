package _09

/*
  추상 클래스
  contents는 구현이 없는 메서드 선언, 다시 말해 contents는 Element의 추상 멤버다
  추상 멤버가 있는 클래스는 추상 클래스로 선언해야 한다.
  추상 클래스는 인스턴스화 할 수 없다.
  추상 멤버는 따로 abstract 수식자가 붙어 있지 않다.
  메서드 - 구현이 없으면 추상 메서드

  파라미터 없는 메서드 vs 빈 괄호 메서드
  관례상, 메서드가 인자도 받지 않고 그 메서드가 속한 객체의 필드를 읽는 방식으로만 접근한다면 파라미터 없는 메서드 사용
  어떤 방식으로 속성을 정의하더라도 클라이언트 코드에는 영향을 끼치지 말아야 한다는 단일 접근 원칙에 부합

  width와, height를 메서드 대신 필드로 정의하기로 결정했다면 각 정의의 def를 val로 변경하기만 하면 된다 (ElementWithField 참고)

  필드 vs 메서드
  클라이언트 관점에서보면 두 개의 정의는 같다. 유일한 차이는 필드를 사용하면 클래스가 초기화될 때 값을 미리 계산하기 때문에 매번 계산을 수행하는 메서드 방식보다 빠르다.
  하지만 필드로 구현하면 각 객체마다 별도의 메모리 공간을 사용한다는 점.

  함수 호출 권장 사항
  스칼라의 함수 호출에서 빈 괄호를 모두 생략할 수도 있지만, 해당 함수 호출이 호출 대상 객체의 프로퍼티에 접근하는 것 이상의 작업을 수행한다면 빈 괄호를 사용하기를 권장

  요약
  스칼라에서는 인자를 받지 않고 부수 효과도 없는 메서드는 파라미터 없는 메서드로 정의할 것을 권장
  한편, 부수 효과가 있다면 필드로 접근하는 것과 동일하게 보일 수 있으므로 관호를 생략해서는 안 된다.
*/

abstract class Element {
  def contents: Array[String]

  // 예제용 임시 메서드
  // 빈 괄호 메서드
  def temp() = 1

  // 파라미터 없는 메서드
  def width: Int = if (height == 0) 0 else contents(0).length

  def height: Int = contents.length

  def above(that: Element): Element =
    new ArrayElementV3(this.contents ++ that.contents)

  def beside(that: Element): Element =
    new ArrayElementV3(
      for (
        (line, line2) <- this.contents zip that.contents
      ) yield line + line2
    )

  override def toString: String = contents mkString "\n"
}

abstract class ElementWithField {
  def contents: Array[String]

  val height: Int = contents.length
  val width: Int = if (height == 0) 0 else contents(0).length
}
