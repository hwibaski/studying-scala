package _05

class Rational(n: Int, d: Int) {
  // 객체 생성될 때 아래의 메시지 출력
  //  println("Created " + n + "/" + d)

  // 선결 조건, 객체 생성 조건 같은 것, IllegalArgumentException 발생 시킴
  require(d != 0)
  private val g = gcd(n.abs, d.abs)
  val numer: Int = n / g
  val denom: Int = d / g

  // 보조 생성자
  // 다만, 보조 생성자는 반드시 같은 클래스에 속한 다른 생성자를 호출하는 코드로 시작해야한다.
  // 모든 보조 생성자의 첫 구문은 this(...) 여야 한다.
  def this(n: Int) = this(n, 1) // 보조 생성자

  override def toString = s"$n/$d"

  //  def add(that: Rational): Rational = {
  //    new Rational(n * that.d + that.n * d, d * that.d) // value d is not a member of _05.Rational, 컴파일 오류
  //    // 대충 느낀 점은 생성자에 주어진 n, d가 외부에서 접근이 안됨. 따라서 파라미터로 주어진 that: Rational에 대해서도 접근이 안될 것
  //  }

  def addV2(that: Rational): Rational = {
    new Rational(
      numer * that.denom + that.numer * denom,
      denom * that.denom
    )
  }

  def +(that: Rational): Rational =
    new Rational(
      numer * that.denom + that.numer * denom,
      denom * that.denom
    )

  def +(i: Int): Rational =
    new Rational(numer + i * denom, denom)

  def -(that: Rational): Rational =
    new Rational(
      numer * that.denom - that.numer * denom,
      denom * that.denom
    )

  def -(i: Int): Rational =
    new Rational(numer - i * denom, denom)

  def *(that: Rational): Rational =
    new Rational(numer * that.numer, denom * that.denom)

  def *(i: Int): Rational =
    new Rational(numer * i, denom)

  def /(that: Rational): Rational =
    new Rational(numer * that.denom, denom * that.numer)

  def /(i: Int): Rational =
    new Rational(numer, denom * i)

  def lessThan(that: Rational) = this.numer + that.denom < that.numer * this.denom

  // 최대 공약수
  private def gcd(a: Int, b: Int): Int =
    if (b == 0) a else gcd(b, a % b)
}


object Main extends App {
  val oneHalf = new Rational(1, 2)
  val twoThirds = new Rational(2, 3)

  println(oneHalf addV2 twoThirds)
}
