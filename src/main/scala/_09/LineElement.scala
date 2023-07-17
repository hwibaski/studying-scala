package _09

/*
  슈퍼클래스 생성자 호출

  슈퍼클래스의 생성자를 호출하려면, 원하는 인자를 슈퍼클래스 이름 뒤에 괄호로 묶어서 넘기면 된다.
 */
class LineElement(s: String) extends ArrayElementV3(Array(s)) {
  override def width: Int = s.length

  override def height = 1
}
