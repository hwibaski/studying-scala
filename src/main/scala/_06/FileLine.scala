package _06

import java.io.File
import scala.language.postfixOps

object FileLine {

  val filesHere = (new File(".")).listFiles

  def fileLines(file: java.io.File) =
    scala.io.Source.fromFile(file).getLines() toArray

  def grep(pattern: String) =
    for (
      file <- filesHere // File[] 순회
      if file.getName.endsWith(".scala"); // filesHere 배열에서 .scala로 끝나는 파일 필터링
      line <- fileLines(file) // 각 파일에서 라인들을 배열로 리턴
      if line.trim.matches(pattern) // 각 라인 필터링
    ) println(s"$file : ${line.trim}")

  // line.trim을 두 번 사용하기 때문에 중간에 변수로 바인딩
  def grepV2(pattern: String) =
    for (
      file <- filesHere
      if file.getName.endsWith(".scala");
      line <- fileLines(file);
      trimmed = line.trim
      if trimmed.matches(pattern)
    ) println(s"$file : ${trimmed}")
}
