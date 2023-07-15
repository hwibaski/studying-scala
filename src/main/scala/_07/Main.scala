package _07

import scala.io.Source

object Main extends App {
  // ====================
  // 1. 메서드
  // ====================

  // 클래스 내부의 함수
  // 일반적인 객체지향에서의 함수

  // ====================
  // 2. 지역 함수
  // ====================

  // 함수 내부의 함수
  // 장점 : 상위 함수 스코프 내에서만 사용할 수 있으므로 프로그램들이 네임스페이스를 오염시키지 않는다.
  // 자바에서는 private 메서드를 이용해서 이를 처리한다.
  // 스칼라에서도 private 메서드가 있지만 지역 함수는 또 다른 방법을 제공한다.
  def processFile(filename: String, width: Int) = {
    def processLine(filename: String, width: Int, line: String) = {
      if (line.length > width)
        println(filename + ": " + line.trim)
    }

    val source = Source.fromFile(filename)
    for (line <- source.getLines()) {
      processLine(filename, width, line)
    }
  }

  // 지역 함수는 상위 함수의 파라미터 및 변수들을 참조할 수 있다.
  // 따라서 코드를 조금 더 개선할 수 있다
  def processFileV2(filename: String, width: Int) = {
    def processLine(line: String) = { // <- 파라미터 개수 줄어들었음
      if (line.length > width)
        println(filename + ": " + line.trim)
    }

    val source = Source.fromFile(filename)
    for (line <- source.getLines()) {
      processLine(line)
    }
  }

  // ====================
  // 3. 1급 계층 함수 (1급 시민, 1급 객체 ...)
  // ====================

  // 함수를 정의하고 호출할 뿐만 아니라 이름 없이 리털로 표기해 값처럼 주고받을 수 있다.
  // 함수 리터럴은 클래스로 컴파일하는데, 해당 클래스를 실행 시점에 인스턴스화하면 함숫값이 된다.
  // 함수 리터럴 ----(컴파일)----> 클래스 ----(인스턴스화)----> 함숫값
  // (x: Int) => x + 1 : 함수 리터럴

  var increase = (x: Int) => x + 1
  println(increase) // _07.Main$$$Lambda$18/0x00000003000eb4f8@612679d6,

  println(increase(10)) // 11

  // increase 변수는 var이기 때문에 다른 함수 할당 가능
  increase = (x: Int) => x + 9999
  println(increase(10)) // 10009

  increase = (x: Int) => {
    println("We")
    println("are")
    println("here!")
    x + 1
  }
  println(increase(10))

  List(1, 2).foreach((x: Int) => println(x))

  // ====================
  // 4. 간단한 형태의 함수 리터럴
  // ====================
  // 인자의 타입을 제거 가능
  // 파라미터의 괄호 제거 가능

  List(1, 2).foreach(x => println(x))

  // ====================
  // 5. 위치 표시자 문법
  // ====================

  // 밑줄(_)을 하나 이상의 파라미터에 대한 위치 표시자로 사용할 수 있다. 단, 함수 리터럴에서 각 인자는 한 번씩만 나타나야 한다.
  // 밑줄을 '채워 넣어야 할 빈칸'으로 생각해도 좋다.
  List(1, 2).filter(_ > 0)

  val f = (_: Int) + (_: Int) // (x => x) + (y => y), (x, y) => x + y
  f(1, 2)

  // ====================
  // 6. 부분 적용한 함수
  // ====================

  // 각 파라미터를 밑줄로 대체도 가능하지만, 전체 파라미터 목록을 밑줄로 바꿀 소도 있다.
  List(1, 2).foreach(x => println(x))
  List(1, 2).foreach(println _)

  // 밑줄을 이런식으로 사용하면 "부분 적용한 함수"로 작성하는 것이다.
  // 부분 적용 함수는 함수에 필요한 인자를 전부 적용하지 않은 표현식을 말한다. 대신에, 인자를 아무것도 제공하지 않거나 일부만 제공한다.
  // 아래와 같이 기존에 만들어진 함수를 이용해서 커스텀하게 새로운 함수를 만들 수 있다.
  // 이렇게 만들 때 편리한 점은 새로운 함수를 정의하지 않아도 된다는 점과 파라미터 설정이 자유롭다

  def sum(a: Int, b: Int, c: Int) = a + b + c

  sum(1, 2, 3)

  val a = sum _ // (Int, Int, Int) => Int
  val b = sum(1, _, _) // (Int, Int) => Int
  val c = sum(1, 2, _) // (Int) => Int
  a(1, 2, 3)
  b(1, 2)
  c(1)

  // 모든 인자가 빠진 println _ 이나 sum _ 같은 부분은 적용 함수 표현시을 적을 때, 함수가 필요한 위치라는 것이 명확하다면 아예 밑줄을 빼고 표기할 수도 있다.
  List(1, 2).foreach(println)

  // val d = sum , 컴파일 안됨, _을 생략할 수 잇는 경우는 foreach와 같이 함수가 필요한 시점 뿐이다.

  // ====================
  // 7. 클로저
  // ====================

  var more = 1

  def temp = (x: Int) => {
    more += 1
    x + more
  }

  println(temp(10))
  more = 1000
  println(temp(10))

  // more는 temp에 할당된 함수 리터럴에서 의미를 부여한 것이 아니기 때문에 자유 변수다.
  // 대조적으로 변수 x는 주어진 함수의 문맥에서만 의미가 있으므로 바운드 변수다.

  // 주어진 함수 리터럴로부터 실행 시점에 만들어낸 객체인 함숫값(객체)을 클로저라고 부른다.
  // 클로저라는 이름은 함수 리터럴의 본문에 있는 모든 자유 변수에 대한 바인딩을 캡쳐링해서 자유 변수가 없게 닫는 행위에서 따온 말이다.
  // (x: Int) => x + 1 처럼 자유 변수가 없는 함수 리터럴을 닫힌 코드 조각(closed term)이라고 부른다.
  // 닫힌 코드 조각은 엄밀히 말해 클로저가 아니다. 이미 닫혀 있기 때문에
  // (x: Int) => x + more 처럼 자유 변수가 있는 함수 리터럴은 열린 코드 조각(open term)이다.
  // 따라서 (x: Int) => x + more을 가지고 실행 시점에 만들어내는 함숫값은 정의에 따라 자유 변수인 more의 바인딩을 캡쳐해야한다.
  // 그렇게 해서 만들어진 함숫값에는 캡처한 more 변수에 대한 참조가 들어 있기 때문에 클로저라 부른다.


  // 직관적으로 스칼라의 클로저는 변수가 참조하는 값이 아닌 변수 자체를 캡쳐링한다.
  // 클로저가 어떤 함수의 지역 변수를 사용하고, 그 함수를 여러 번 호출한다면 어떻게 될까?
  // 매번 클로저가 그 변수에 접근할 때 어떤 변수를 사용하게 될까?
  // 답은 클로저를 만들 때 사용할 수 있었던 인스턴스를 사용한다.


  // adder 파라미터는 함수 리터럴 외부 값이므로 자유 변수이다.
  def makeIncreaser(adder: Int) = (x: Int) => x + adder

  // 클로저가 생기고 그 안에서 adder를 1로 바인딩
  println(makeIncreaser(1)) // _07.Main$$$Lambda$34/0x0000000300135000@4d1b0d2a

  // 클로저가 생기고 그 안에서 adder를 9999로 바인딩
  println(makeIncreaser(9999)) // _07.Main$$$Lambda$34/0x0000000300135000@954b04f

  // 클로저 생성 시 자유변수의 값이 어떤 것이었는지에 따라 결과가 달라진다.

  // adder 변수가 스택에서 제거되어도 컴파일러가 클로저를 만들어내는 메서드보다 더 오래 살아남을 수 있게 힙에 재배치하기 때문에 adder의 상태를 변경할 수 있다.

  // ====================
  // 8. 특별한 형태의 함수 호출
  // ====================

  // 반복 파라미터 (다중 파라미터)

  def echo(args: String*) =
    for (arg <- args) println(arg)

  echo("one")
  echo("one", "two")

  val seq = Seq("One", "Two", "Three")
  // Seq타입을 전달하기 위해서는 변수명: _* 을 추가해야 한다.
  echo(seq: _*)

  // 이름 붙인 인자
  def speed(distance: Float, time: Float): Float = {
    distance / time
  }

  speed(distance = 100, time = 10)
  speed(time = 10, distance = 100)

  // 디폴트 인자
  def printTime(out: java.io.PrintStream = Console.out,
                divisor: Int = 1) =
    out.println("time = " + System.currentTimeMillis() / divisor)

  printTime(out = Console.err)
  printTime(divisor = 1000)

  // ====================
  // 9. 꼬리 재귀
  // ====================

  // var를 사용하지 않기 위해 val 만 사용하는 함수형 스타일로 바꾸려면 재귀를 사용해야 할 수도 있다고 언급했다.

  def approximate(guess: Double): Double =
    if (isGoodEnough(guess)) guess
    else approximate(improve(guess))

  def approximateLoop(initialGuess: Double): Double = {
    var guess = initialGuess
    while (!isGoodEnough(guess))
      guess = improve(guess)
    guess
  }

  // 함수의 구현 내용이 중요한 것이 아님, 컴파일 에러 피하기 위한 임시 코드
  def isGoodEnough(d: Double) = true

  def improve(d: Double) = 0

  // approximate와 approximateLoop 중 간결셩이나 var를 피한다는 측면에서는 함수형이 우세하다.
  // 하지만 명령형 스타일의 접근이 좀 더 효율적이지 않나? 사실 수행 시간을 측정해보면 두 가지 방법이 거의 동일하다.
  // 보통 루프의 끝에서 시작 부분으로 가는 것보다 재귀 호출이 훨씬 비용이 많이 드는 것처럼 보이기 때문에 의아할지도 모르겠다.
  // 하지만 위의 근사치 추정 같은 경우에는 스칼라 컴파일러가 중요한 최적화를 적용할 수 있다.
  // approximate 함수와 같이 마지막에 자신을 재귀 호출하는 경우를 꼬리 재귀라고 한다.
  // 스칼라 컴파일러는 꼬리 재귀를 감지해 다음에 사용할 새로운 값과 함께 함수의 첫 부분으로 돌아가는 내용으로 변경한다.
  // 즉, 꼬리 재귀를 이용하면 컴파일러가 최적화를 도와준다.

  // 꼬리 재귀의 한계

  // isEven과 isOdd는 자기 자신을 호출하지 않기 때문에 최적화 불가능하다.
  def isEven(x: Int): Boolean =
    if (x == 0) true else isOdd(x - 1)

  def isOdd(x: Int): Boolean =
    if (x == 0) false else isEven(x - 1)

  val funValue = nestedFun _

  def nestedFun(x: Int): Unit = {
    if (x != 0) {
      println(x)
      funValue(x - 1)
    }
  }

  funValue(1) // funValue 자기 자신이 함수가 아니라 nestedFun의 호출을 감싼 함숫값을 가리키기 때문에 이런 경우에는 꼬리 재귀 최적화가 일어나지 않는다.

  def foo(x: Int): Int =
    if (x % 2 == 1)
      foo(x / 2)
    else 1 + foo(x / 2) // 재귀에 추가적인 연산을 하기 때문에 꼬리 재귀가 아니다. 따라서 최적화 불가

}
