package _02

import scala.collection.immutable.HashSet
import scala.collection.mutable

object Main extends App {
  // 1. 배열
  // 추천하는 방법 아님
  val greetStrings = new Array[String](3)

  greetStrings(0) = "Hello"
  greetStrings(1) = ", "
  greetStrings(2) = "World\n"

  for (i <- 0 to 2)
    print(greetStrings(i))

  println("----------------------------------------------------------")

  // 위의 코드를 아래와 같이 변환한다.
  val greetStringsV2 = new Array[String](3)

  greetStringsV2.update(0, "Hello")
  greetStringsV2.update(1, ", ")
  greetStringsV2.update(2, "World\n")

  // 2. 리스트
  // 같은 타입의 객체로 이루어진 변경 불가능한 시퀀스
  // ::: -> 두 개의 리스트를 합쳐서 새로운 리스트 반환
  // :: -> 새 원소를 기존 리스트의 맨 앞에 추가한 새로운 리스트를 반환

  val oneToThree = List(1, 2, 3)

  val oneTwo = List(1, 2)
  val threeFour = List(3, 4)
  val oneTwoThreeFour = oneTwo ::: threeFour
  println(oneTwo + " and " + threeFour + " were not mutated")
  println(oneTwoThreeFour + " is a new list")

  val twoThree = List(2, 3)
  val oneTwoThree = 1 :: twoThree
  println(oneTwoThree)

//  val oneTwoThree = 1 :: 2 :: 3 :: Nil -> List(1, 2, 3)

  println("----------------------------------------------------------")

  // 3. 튜플
  val pair = (99, "Luftballons")
  // 1 부터 시작함, _N이라는 접근자 사용
  println(pair._1) // 99
  println(pair._2) // "Luftballons"

  println("----------------------------------------------------------")

  // 4. 집합과 맵
  // 변경 가능한 컬렉션 vs 변경 불가능한 컬렉션
  // 배열은 항상 변경 가능하지만 리스트는 항상 변경 불가능하다.
  // 하지만 집합이나 맵에 대해서는 변경 가능한 것과 변경 불가능한 것을 모두 제공한다.
  //
  // scala.collection Set <<trait>> -> scala.collection.immutable Set -> scala.collection.immutable HashSet
  //                               ↘︎ scala.collection.mutable Set   -> scala.collection.mutable HashSet

  val jetSet = Set("Boeing", "Airbus")
//  jetSet += "Lear" // compile 불가
  val newJestSet = jetSet + "Lear" // jetSet 에다가 "Lear"를 추가한 다음 아예 새로운 Set 리턴하는 방식
  println(jetSet.contains("Lear"))
  println(newJestSet)

  val movieSet = mutable.Set("Hitch", "Politergeist")
  movieSet += "Shrek" // 기존 movieSet에 "Shrek" 추가하는 방식
  println(movieSet)

  val hashSet = HashSet("Tomatoes", "Chilies")
  println(hashSet + "Coriander") // 새로운 HashSet 리턴

  println("----------------------------------------------------------")

  // scala.collection Map <<trait>> -> scala.collection.immutable Map -> scala.collection.immutable HashMap
  //                               ↘︎ scala.collection.mutable Map   -> scala.collection.mutable HashMap

  val treasureMap = mutable.Map[Int, String]()
  treasureMap += (1 -> "Go to island.")
  treasureMap += (2 -> "Find big X on Ground")
  treasureMap += (3 -> "Dig.")
  println(treasureMap(2))

  val romanNumeral = Map(1 -> "I", 2 -> "II", 3 -> "III", 4 -> "IV", 5 -> "V")
  println(romanNumeral)

  println("----------------------------------------------------------")

  // 5. 함수형 스타일을 인식하는 법

  def printStringArr(arr: Array[String]): Unit = {
    var i = 0
    while (i < arr.length) {
      println(arr(i))
      i += 1
    }
  }

  // var 변수, 변경 가능한 변수 제거

  def printStringArrV2(arr: Array[String]): Unit = {
    for (elem <- arr) {
      println(elem)
    }
  }

  // 리턴 타입이 Unit인 함수는 부수효과를 발생, 여기서의 부수 효과는 println()
  // 어떤 함수가 관심의 대상이 될 만한 값을 반환하지 않는다면 그런 함수가 주변 세계에 영향을 끼칠 수 있는 유일한 방법은 어떤 형태로든 부수 효과를 통하는 것일 수 밖에 없다.
  def printStringArrV3(arr: Array[String]): Unit =
    arr.foreach(println)

  // 함수 자체에서는 부수효과를 제거해보자
  // 모든 유용한 프로그램에는 어떤 형태로든 부수 효과가 들어가기 마련이다. 하지만 부수 효과가 없는 메서드를 더 우선시하면, 부수 효과가 있는 코드를 최소한으로 사용하면서 안전하게 프로그래밍 가능하다
  // 이런 코드의 장점은 코드를 테스트하기 좋다는 점이다.
  def printStringArrV4(arr: Array[String]): String = arr.mkString("\n")
  println(printStringArrV4(Array("a", "b", "c")))

  val res = printStringArrV4(Array("c", "b", "d"))
  assert(res == "c\nb\nd")

  println("----------------------------------------------------------")

}
