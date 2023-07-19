# 트레이트

스칼라에서 트레이트는 코드 재사용의 근간을 이루는 단위다.

트레이트를 유용하게 써먹는 가장 일반적인 방법 두 가지

1. 간결한 인터페이스를 만드는 것
2. 쌓을 수 있는 변경을 정의하는 것

## 트레이트의 동작 원리

- trait 키워드를 사용한다는 점을 제외하면 클래스의 정의와 같다.
- AnyRef가 슈퍼클래스다
- 스칼라 프로그래머는 트레이트를 사용할 때 상속보다는 `믹스인`을 사용하려고 한다.
- 트레이트를 믹스인할 때는 extends 키워드를 사용한다. extends를 사용하면 트레이트의 슈퍼클래스를 암시적으로 상속한다.

```scala
trait Philosophical {
  def phiolosophize = {
    println("I consume memory, therefore I am!")
  }
}
```

```scala
// Philosophical을 믹스인한 클래스의 예, (이게 상속 아니야?)
class Frog extends Philosophical {
  override def toString = "green"
}

// Frog 클래스는 Philosophical의 슈퍼클래스인 AnyRef의 서브 클래스이며, Philosophical을 믹스인한다.

val frog = new Frog()
forg.philosphize(); // "I consume memory, therefore I am!"

// 다형성
val phil: Philosopical = forg
phil.philosphize() //"I consume memory, therefore I am!" 
```

트레이트를 어떤 슈퍼클래스를 명시적으로 상속받은 클래스에 혼합할 수 도 있다. extends 키워드를 사용해 슈퍼클래스를 지정하고 with을 사용해 트레이트를 믹스인한다.

```scala
class Animal

class Frog extends Animal with Philosophical {
  override def toString = "green"
}
```

- 여러 트레이트의 믹스인

```scala
class Animal

trait HasLegs

class Frog extends Animal with Philosophical with HasLegs {
  override def toString = "green"
}
```

- Frog 클래스는 Philosophical 트레이트의 philosophize 구현을 그대로 상속했다. Frog 클래스에서 philosophize를 오버라이드 할 수 있다.

```scala
class Animal

class Frog extends Animal with Philosophical {
  override def toString = "green"

  override def philosophize() = {
    println("It ain't easy being " + toString + "!")
  }
}
```

- Philosophical을 with으로 믹스인해도 Philosopical 타입의 변수에 사용할 수 있다.

## 클래스와 트레이트의 문법적 차이 2가지

1. 트레이트느느 클래스 파라미터를 가질 수 없다.

```scala
class Point(x: Int, y: Int)

trait NoPoint(x: Int, y: Int) // 컴파일 할 수 없다.
```

2. 클래스에서는 super 호출을 정적으로 바인징하지만, 트레이트에서는 동적으로 바인딩한다는 점이다.

## 트레이트 예제

```scala
class Point(val x: Int, val y: Int)

// Rectangler과 Component 클래스에 공통적인 로직을 따로 빼서 trait으로 만들고 제공
class Rectangle(val topLeft: Point, val bottomRight: Point) {
  def left = topLeft.x

  def right = bottomRight.x

  def width = right - left
}

abstract class Component {
  def topLeft: Point

  def bottomRight: Point

  def left = topLeft.x

  def right = bottomRight.x

  def width = right - left
}

trait Rectangular {
  def topLeft: Point

  def bottomRight: Point

  def left = topLeft.x

  def right = bottomRight.x

  def width = right - left
}

abstract class ComponentWithTrait extends Rectangular

class RectangleWithTrait(val topLeft: Point, val bottomRight: Point) extends Rectangular
```

## Ordered 트레이트

- 순서가 있는 두 객체를 비교할 때마다 한 번의 호출만으로 원하는 비교를 정확히 할 수 있다면 편할 것.
- compare 메서드는 호출 대상 객체와 인자로 전달받은 객체를 비교하고, 두 객체가 동일하면 0, 호출 대상 객체 자신이 인자보다 작으면 음수, 더 크면 양수를 반환해야한다.
- Ordered를 믹스인 하더라도 equals는 직접 정의해야 한다. 이를 우회할 수 있는 방법은 30장에서 익힌다.

```scala
// Ordered Trait 사용전
class Rational(n: Int, d: Int) {
  // ...
  def <(that: Rational) =
    this.numer * that.denom < that.numer * this.denom

  def >(that: Rational) =
    that < this

  def <=(that: Rational) =
    (this < that) || (this == that)

  def >=(that: Rational) =
    (this > that) || (this == that)
}

class Rational(n: Int, d: Int) extends Ordered[Rational] {
  def compare(that: Rational) =
    (this.numer * that.denom) - (that.number * this.denom)
}

val half = new Rational(1, 2)

val third = new Rational(1, 3)

half > third // false
```

## 트레이트를 이용해 변경 쌓아올리기

- 트레이트를 사용하면 클래스의 메서드를 변경할 수 있을 뿐만 아니라, 이런 변경 위에 다른 변경을 계속 쌓을 수 있다.

```scala
abstract class IntQueue {
  def get(): Int

  def put(x: Int): Unit
}

class BasicIntQueue extends IntQueue {
  private val buf = new ArrayBuffer[Int]()

  override def get(): Int = buf.remove(0)

  override def put(x: Int): Unit = {
    buf += x
  }
}

val queue = new BasicIntQueue
queue.put(10)
queue.put(20)
queue.get() // 10
queue.get() // 20
```

## 아래의 트레이트들의 특이점

1. 슈퍼클래스로 IntQueue를 선언한다는 것.
    - 이 선언은 Doubling 트레이트가 IntQueue를 상속한 클래스에만 믹스인될 수 있다는 뜻이다.
2. 트레이트의 추상 메서드가 super를 호출한다는 점
    - 컴파일러에세 의도적으로 super를 호출했다는 사실을 알려주기 위해, abstract override 표시
    - 이 표시는 트레이트에서만 사용가능
    - abstract override 메서드가 어떤 트레이트에 있다면 그 트레이트는 반드시 abstract override가 붙은 메서드에 대한 구체적 구현을 제공하는 클래스에 믹스해야한다.

```scala
import _11.tmp.BasicIntQueue

trait Doubling extends IntQueue {
  abstract override def put(x: Int): Unit = {
    super.put(2 * x)
  }
}

trait Incrementing extends IntQueue {
  abstract override def put(x: Int): Unit = {
    super.put(x + 1)
  }
}

trait Filtering extends IntQueue {
  abstract override def put(x: Int): Unit = {
    if (x >= 0) super.put(x)
  }
}

class MyQueue extends BasicIntQueue with Double

val queue = new MyQueue
queue.put(10)
queue.get() // 20
```

- new를 이용해 인스턴스를 생성하면서 트레이트 믹스인하기

```scala
val queue = new BasicIntQueue with Doubling
queue.put(10)
queue.get() // 20
```

- 같은 구현을 가지고 있는 여러 트레이트를 믹스인하기
- 믹스인의 순서가 중요하다. 간단하게는 가장 오른쪽에 있는 트레이트의 효과를 먼저 적용한다.

```scala
val queue = new BasicIntQueue with Incrementing with Filtering
queue.put(-1) // Filtering 트레이트에 걸려서 큐에 들어가지 않는다.
queue.put(0) // Incrementing 트레이트에서 1이 더해져서 큐에 들어간다
queue.put(1) // Incrementing 트레이트에서 1이 더해져서 큐에 들어간다

queue.get() // 1
queue.get() // 2
```
