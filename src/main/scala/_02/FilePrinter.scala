package _02

import scala.io.Source

object FilePrinter extends App {
  val tempArr = Array("temp.txt")
//  if (tempArr.length > 0)
//    for (line <- Source.fromFile(tempArr(0)).getLines())
//      println(line.length.toString + " " + line)
//  else
//    Console.err.println("Please enter filename")

//    원본 파일 내용
//    abcde
//    1234
//    fghijk

  val lines = Source.fromFile(tempArr(0)).getLines().toList

  println(lines) // List(abcde, 1234, fghijk)

  // s 의 길이(int)를 문자로 변환한 것의 길이
  def widthOfLength(s: String) = s.length.toString.length

  // 파일의 각 중에서 가장
  var maxWidth = 0
//  for (line <- lines)
//    maxWidth = maxWidth.max(widthOfLength(line))

  // 인자로 받은 두 문자열 중 긴 문자열을 반환하기 때문에 그 결과는 lines에서 지금까지 함수에 전달했던 각 줄의 길이 중 최댓값이다.
  // 가장 긴 문자열 반환
  val longestLine = lines.reduceLeft((a, b) => if (a.length > b.length) a else b)

  maxWidth = widthOfLength(longestLine)

  // 1. lines 변수에 해당 파일의 각 줄의 내용을 list로 가지고 있음
  for (line <- lines) {
    val numSpaces = maxWidth - widthOfLength(line)
    val padding = " " * numSpaces
    println(padding + line.length + " | " + line)
  }
}
