package _04

import scala.language.postfixOps

object Main extends App {
  val longVal = 10L
  val intVal = 10

  val big = 1.2345 // Double
  val bigger = 1.2345e1 // 12.345

  val floatVal = 1.2345f

  println(
    """Welcome to Ultamix 3000.
             Type "HELP" for help""")

  println(
    """Welcome to Ultamix 3000.
      |Type "HELP" for help""".stripMargin)

  val s = Symbol("a")
  println(s.name) // a

  val name = "reader"
  println(s"Hello, $name!")

  println(raw"No\\\\\escape!")

  val doubleVal = 1.2345
  println(f"${doubleVal}%.2f") // 1.23

  println("----------------------------------------------------------")

  // 중위 연산자 표기법, 메서드를 연산자 처럼 표기할 수 있음
  // 객체와 파라미터 사이에 위치한 연산자
  // 아래의 예에서 연산자는 indexOf, 객체와 파라미터 사이에 있음.
  val s2 = "HelloWorld"
  val i = s2.indexOf("o")

  val i2 = s2 indexOf "o"

  val i3 = s2 indexOf("0", 2)

  // 전위, 단항 연산자, 피연산자가 하나뿐이다. (+, -, !, ~) 이 네가지 뿐이다.
  // 피연산자는 연산자 오른쪽에 온다.
  // ex) -2.0, !found, ~0xFF

  val prefixOp = -2.0 // (2.0).unary_- 를 호출한다.

  // 후위 - 인자를 취하지 않는 메서드를 '.'이나 괄호 없이 호출하는 경우
  // 스칼라에서는 메서드 호출 시 빈 괄호를 생략할 수 있다.
  // 관례상 메서드에 부수 효과가 있다면 괄호를 넣고 그렇지 않다면 아래 있는 문자열에 대한 toLowerCase 호출처럼 괄호를 사용하지 않는다.

  val str = s2 toLowerCase

  println(str)

  println("----------------------------------------------------------")

  // 객체 동일성, 모든 객체에 적용가능, 서로 다른 타입에도 적용가능

  println(1 == 2)
  println(List(1, 2) == List(1, 2))
  println(1 == 1.0)
  println(List(1) == "hello")

  // null과도 비교가능
  println(null == 1)
  println(List(1) == null)

  // 스칼라가 좌항이 null인지 검사하고, 좌항이 null이 아니라면 해당 객체의 equals 메서드를 호출한다.
  // 따라서 위의 경우에서는 자동으로 null을 확인하기 때문에 직접 null을 검사할 필요가 없다.

  // 아래의 케이스에서는 참조 동일성일 확인함
  println(List(1, 2).eq(List(1, 2))) // false
}
