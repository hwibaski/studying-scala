package _08

import java.io.{File, PrintWriter}

object Main extends App {
  // ====================
  // 커링
  // ====================

  def plainOldSum(x: Int, y: Int) = x + y

  plainOldSum(1, 2)

  def curriedSum(x: Int)(y: Int) = x + y

  curriedSum(1)(2)

  // curriedSum을 호출하는 것은 실제로 2개의 함수를 연달아서 호출하는 것이다.
  // 첫 번째 함수 호출은 Int 타입인 x를 인자로 받고, 호출 가능한 함숫값을 반환한다.
  /**
   * curriedSum(1)이 실행되었을 때 아래와 같은 함수과 반환된다고 생각하자.
   * def fist(x: Int) = (y: Int) => x + y
   *
   * val second = fist(1)
   * val res = second(2) // 3
   */

  // 두 번째 파라미터 자리에 플레이스 홀더 (위치 표시자)를 이용하면 first(1)과 같은 함수를 얻어낼 수 있다.
  val onePlus = curriedSum(1) _
  val res = onePlus(2) // 3
  val twoPlus = curriedSum(2) _
  val res2 = twoPlus(2) // 4

  // ====================
  // 새로운 제어 구조 작성, (빌려주기 패턴)
  // ====================

  def twice(op: Double => Double, x: Double) = op(op(x))

  twice(_ + 1, 5) // 7.0, 5에다가 1을 두 번 더한다.

  // 아래의 메서드를 사용하는 경우, 사용자 코드가 아니라 withPrintWriter가 파일 닫기를 보장한다는 장점이 있다. 이러한 패턴을 빌려주기 패턴이라고 한다.
  def withPrintWriter(file: File, op: PrintWriter => Unit) = {
    val writer = new PrintWriter(file)
    try {
      op(writer)
    } finally {
      writer.close()
    }
  }

  withPrintWriter(
    new File("date.txt"),
    writer => writer.println(new java.util.Date)
  )

  // 스칼라에서는 어떤 메서드를 호출하든 인자를 하나만 전달하는 경우는 소괄호 대신 중괄호를 사용할 수 있다.
  // 이렇게 사용할 수 있게 한 이유는 클라이언트 프로그래머가 중괄호 내부에 함수 리터럴을 사용하도록 하기 위해서다
  println("Hello, world")
  println {
    "Hello, World"
  }

  def withPrintWriterV2(file: File)(op: PrintWriter => Unit) = {
    val writer = new PrintWriter(file)
    try {
      op(writer)
    } finally {
      writer.close()
    }
  }

  // 커링과 조합해서 아래와 같이 사용 가능, 뭔가 언어 차원에서 기본제공해주는 것 같은 모양을 만들 수 있네...
  withPrintWriterV2(new File("date.txt")) {
    writer => writer.println(new java.util.Date)
  }

  // ====================
  // 이름에 의한 호출 파라미터
  // ====================
  /**
   * if, while과 좀 더 유사하게, 중괄호 사이에 값을 전달하는 내용이 없는 형태로 구현하고 싶다면 어떻게 해야할까?
   * 그러한 사황에 스칼라에서 사용할 수 있는 것이 이름에 의한 호출 파라미터다.
   *
   * withPrintWriterV2(new File("date.txt")) {
   * writer => writer.println(new java.util.Date)
   * }
   */

  val assertionEnabled = true

  def myAssertV1(predicate: () => Boolean) =
    if (assertionEnabled && !predicate())
      throw new AssertionError

  myAssertV1(() => 5 > 3)

  def myAssertV2(predicate: Boolean) =
    if (assertionEnabled && !predicate)
      throw new AssertionError

  myAssertV2(5 > 3)

  def myAssertV3(predicate: => Boolean) =
    if (assertionEnabled && !predicate)
      throw new AssertionError

  myAssertV3(5 > 3)

  /**
   * predicate 에 Boolean을 사용하지 않는 이유는 myAssertV2() 함수가 실행되기 전에 5 > 3이 먼저 실행되버린다.
   * 하지만 myAssertV3는 assertEnabled가 false라면 predicate 파라미터를 평가하지 않는다.
   * myAssertV1 처럼 사용하면 V3와 효과는 똑같지만 스칼라에서 이름에 의한 호출 파라미터랑 기능을 제공하므로 사용하자
   */
}
