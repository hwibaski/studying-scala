package _01

object Main extends App {
  println("hello")
  println("max :", max(1, 2))
  println("---------------------------------------------")
  greet
  greet()

  println("---------------------------------------------")
  // 바람직한 스칼라 프로그램은 아니다. 인덱스를 이용하는 것보다 더 좋은 접근 방법을 배울 것이다.
  val arr = Array("a", "b", "c")
  var i = 0
  while (i < arr.length) {
    println(arr(i))
    i += 1
  }

  println("---------------------------------------------")

  {
    var i = 0
    while (i < arr.length) {
      if (i != 0)
        print(" ")
      println(arr(i))
      i += 1
    }
    println()
  }

  println("---------------------------------------------")

  arr.foreach(el => println(el))

  println("---------------------------------------------")

  arr.foreach((el: String) => println(el))

  println("---------------------------------------------")

  for (el <- arr)
    println(el)

  def max(x: Int, y: Int): Int =
    if (x > y) x
    else y

  def greet(): Unit = println("Hello, World")
}
