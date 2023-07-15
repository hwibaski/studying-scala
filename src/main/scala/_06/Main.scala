package _06

import java.io.{FileNotFoundException, FileReader, IOException}
import java.net.{MalformedURLException, URL}
import scala.io.StdIn.readLine

object Main extends App {
  // 스칼라 제어 구문은 대부분 어떤 값을 결과로 내놓는다.
  // for, try, match, if ...
  // 변수를 줄일 수 있다.

  // ====================
  // 1. if 표현식
  // ====================

  val arr = Array("1.txt", "2.txt")

  // 명령형 스타일
  var fileName = "default.txt"
  if (!arr.isEmpty)
    fileName = arr(0)


  // 함수형 스타일
  // var 사용을 하지 않았음, 부수효과가 없음
  // val을 사용할 기회를 노려보자. val은 더 가독성 높고 리팩토링하기 쉽게 해준다.
  val fileName2 = if (!arr.isEmpty) arr(0)
  else "default.txt"

  // ====================
  // 2. while 루프
  // ====================

  // iterable한 최대 공약수
  def gcdLoop(x: Long, y: Long): Long = {
    var a = x
    var b = y
    while (a != 0) {
      val temp = a
      a = b % a
      b = temp
    }
    b
  }

  // 재귀를 이용한 최대 공약수
  def gcd(x: Long, y: Long): Long =
    if (y == 0) x else gcd(y, x % y)

  // ====================
  // 3. do-while
  // ====================

  var line = ""
  do {
    line = readLine()
    println("Read: " + line)
  } while (line != "")


  // 자바에서는 아래의 코드가 readLine()의 값을 line2에 할당하고, line2가 ""이 아닌지 판별한다.
  // 하지만 스칼라에서는 할당 연산의 결과는 언제나 Unit이다. 따라서 line2 = readLine()은 Unit이 되고 Unit과 빈문자열을 검사하게 된다.
  // Unit은 빈문자열과 같을 수 없으므로 무한 루프에 빠진다
  var line2 = ""
  while ((line2 = readLine()) != "")
    println("Read :" + line2)

  // while 루프는 일반적으로 var변수를 사용하거나, I/O를 수행하거나 한다.
  // while과 var를 사용하기 전에 꼭 사용해야하는지, 다른 방법은 없는지 고민해보자.

  // ====================
  // 4. for
  // ====================

  // 컬렉션 이터레이션
  val strList = List("apple", "bee", "cake")

  for (str <- strList)
    println(str)

  for (i <- 1 to 4) // 1 to 4는 Range(1, 2, 3, 4) 라는 컬렉션을 리턴한다.
    println(i)

  for (i <- 1 until 4) // 1 ~ 3, 4제외
    println(i)

  for (str <- 0 to strList.length - 1) // 이런 방법은 스칼라에서는 일반적이지 않다!!!!!
    println(str)

  // 필터링
  // 방법 A
  for (str <- strList if str.startsWith("a"))
    println(str)

  // 방법 B
  // 명령형스러운 방법
  for (str <- strList)
    if (str.startsWith("a"))
      println(str)

  // 방법 A를 응용, filter 로직을 추가하기 편함
  for (
    str <- strList
    if str.length > 3
    if str.startsWith("a")
  ) println(str)

  // 중첩 이터레이션
  // <- 절을 추가하면 중첩 루프 작성 가능
  // FileLine object 참고

  // 새로운 컬렉션 만들어내기
  // yield를 사용해서 새로운 컬렉션 만들기,
  // strList가 List 타입이고 그 안의 요소인 str을 yield 하므로 List[String] 리턴함
  val newStr = for (
    str <- strList
    if str.startsWith("a")
  ) yield str

  // List[Integer]
  val newStrLength = for (
    str <- strList
    if str.startsWith("a")
  ) yield str.length

  // ====================
  // 5. try
  // ====================

  // 예외 던지기
  // throw new IllegalAccessException

  val n = 1;

  // half에 할당되기 전에 예외 발생
  val half =
    if (n % 2 == 0)
      n / 2
    else
      throw new RuntimeException("n must be even")

  try {
    val f = new FileReader("input.txt")
    // 파일을 사용하고 닫는다.
  } catch {
    case ex: FileNotFoundException => println("파일 못 찾음")
    case ex: IOException => println("io 예외")
  } finally {
    println("여기는 무조건 실행")
  }

  // catch 에서 값을 리턴함
  def urlFor(path: String) =
    try {
      new URL(path)
    } catch {
      case e: MalformedURLException => new URL("http://www.scala-lang.org")
    }

  // 2 return
  def f(): Int = try return 1 finally return 2

  // 1 return , finally는 값을 만들어내거나 리턴하지말자.. 헷갈리니까...부수 효과를 제공하는 방법이라고만 생각하자
  def g(): Int = try 1 finally 2

  // ====================
  // 6. match
  // ====================

  // 다수의 대안 중 하나를 선택하게 해준다. pattern을 사용해 원하는 내용을 선택
  // 완전히 알려지지 않은 값을 표기하기 위한 위치 표시자로 '_' 를 사용한다

  val tempList = List("a", "b", "c")
  val firstElem = if (tempList.nonEmpty) tempList(0) else ""

  firstElem match {
    case "a" => println("I am A")
    case "b" => println("I am B")
    case _ => println("I am not A, B")
  }

  val result = firstElem match {
    case "a" => "I am A"
    case "b" => "I am B"
    case _ => "I am not A, B"
  }

  // match의 결과 또한 값이다.
  println(result)

  // ====================
  // 7. break와 continue 문 없이 살기
  // ====================

  // break와 continue는 8장에서 설명할 함수 리터럴과 어울리지 않기 때문에 스칼라에서는 제외했다.
  /**
   * // 자바 코드로 인자 목록에서 .scala로 끝나고 하이픈으로 시작하지 않는 문자열을 검색하는 코드
   * int i = 0
   * boolean foundIt = false
   *
   * while (i < args.length) {
   * if (args[i].startsWith("-")) {
   * i = i + 1;
   * continue;
   * }
   * if (args[i].endsWith(".scala)) {
   * foundIt = true;
   * break;
   * }
   * i = i + 1;
   * }
   */

  // break 문 대신 while 문 안에 foundIt에 대한 조건 집어 넣었음
  // continue를 없애기 위해 if (args[i].startsWith("-") 조건을 역으로 바꾸고 아래의 실행될 코드를 감쌌음
  var i = 0
  var foundIt = false
  while (i < args.length && !foundIt) {
    if (!args(i).startsWith("-")) {
      if (args(i).endsWith(".scala"))
        foundIt = true
    }
    i = i + 1
  }

  // var를 제거한 버전 - 재귀... 어렵다..

  def searchFrom(i: Int): Int = {
    if (i >= args.length) -1
    else if (args(i).startsWith("-")) searchFrom(i + 1)
    else if (args(i).endsWith(".scala")) i
    else searchFrom(i + 1)
  }

}
