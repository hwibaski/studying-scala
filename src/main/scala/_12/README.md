# 패키지와 임포트

- 특히 규모가 큰 프로그램을 작성할 때는 프로그램의 여러 부분이 서로 의존하는 정도를 나타내는 커플링을 최소화하는 것이 중요하다
- 커플링을 최소화하는 방법 중 하나는 모듈화 스타일로 프로그램을 작성하는 것이다.

## 패키지 안에 코드 작성하기

- bobsrockets.navigation 패키지 안에 Navigator 클래스를 넣는다.

```scala
package bobsrockets.natigation

class Navigator
```

```scala
// 위의 예제와 동일한 기능 수행
package bobsrockets.navigation {
  class Navigator
}
```

`한 파일에 여러 패키지를 넣을 때`는 더 일반적인 방식을 사용한다

```scala
package bobsrockets {
  package navigation {
    // bobsrockets.navigation 패키지 안쪽
    class Navigator
    package tests {
      //bobsrockets.navigation.tests 패키지 안쪽
      class NavigatorSuite
    }

  }

}
```

## 관련 코드에 간결하게 접근하기

```scala
package bobsrockets {

  package navigation {
    class Navigator {
      // 같은 패키지 내에 있는 클래스 이므로 패키지 경로 안써도 된다.
      val map = new StarMap
    }

    class StarMap
  }

  class Ship {
    // navigation 패키지 안쪽이 아니므로 navigation.Navigator
    // 그러나 같은 bobsrockets 패키지 안쪽이므로 bobsrockets는 쓸 필요가 없다.
    val nav = new navigation.Navigator
  }
  package fleets {
    class Fleet {
      // bobsrockets.Ship을 쓸 필요가 없다.
      // 이미 bobsrockets 패키지 내부이므로
      def addShip() = new Ship
    }
  }

}

```

```scala
// launch.scala 파일
package launch {
  class Booster3
}

// bobsrockets.scala 파일
package bobsrockets {
  package navigation {
    package navigation {
      package launch {
        class Booster1
      }

      class MissionControl {
        val booster1 = new launch.Booster1
        val booster2 = new bobsrockets.launch.Booster2
        val booster3 = new _root_.launch.Booster3
      }
    }

  }

  package launch {
    class Booster2
  }

}
```

`_root_` 패키지 제공. 모든 최상위 패키지는 _root_ 패키지의 멤버로 취급한다.

## import

```scala
package bobsdelights

abstract class Fruit(val name: String, val color: String)

object Fruits {
  object Apple extends Fruit("apple", "red")

  object Orange extends Fruit("orange", "orange")

  object Pear extends Fruit("pear", "yellowish")

  val menu = List(Apple, Orange, Pear)
}

// Fruit에 간단하게 접근

import bobsdelights.Fruit

// bobsdelights의 모든 멤버에 간단하게 접근
import bobsdelights._

// Fruits의 멤버에 간단하게 접근
import bobsdelights.Fruits._

def shofFruit(fruit: Fruit) = {
  import fruit._ // 컴파일 단위의 시작 부분뿐만 아니라 코드의 어디에라도 들어갈 수 있다.
  println(name + "s are" + color)
}

// Fruits 객체에 있는 Apple과 Orange만을 불러온다.

import Fruits.{Apple, Orange}

// Fruits 객체에 잇는 Apple과 Orange만을 불러온다. 다만 Apple의 이름을 다른 이름으로 매핑할 수 있다.
// <원래 이름> => <새이름>
import Fruits.{Apple => McIntosh, Orange}
import java.sql.{Date => SDate}

// import Fruits._와 동일, Fruits 객체로부터 모든 멤버를 불러온다.
import Fruits.{_}

// Fruits 객체의 모든 멤버를 불러오나, Apple의 이름을 McIntosh로 바꾼다.
import Fruits.{Apple => McIntosh, _}

// Fruits에서 Pear를 제외한 모든 멤버를 불러온다.
import Fruits.{Pear => _, _}
```

- 간단한 이름 x. `이런 임포트가 있으면, 불러온 이름이 집합에 x를 추가한다.
- 이름 변경 절 x => y가 있으면 x 멤버를 y라는 일므으로 볼 수 있게 한다.
- 숨김 절 x => _. 불러온 이름의 집합에서 x를 제외한다.
- 나머지를 모두 가져오는 '_'.이 '_'직전까지 있는 임포트 절에서 언급한 멤버들을 제외한 모든 멤버를 불러온다. 이 구문은 항상 임포트 셀렉터 중 맨 나중에 와야한다.

## 접근 수식자

패키지, 클래스, 객체 멤버 앞에 private와 protected 접근 수식자를 둘 수 있다.
자바와 비슷하지만 중요한 차이가 몇 가지 있다.

### 비공개 멤버

- private가 앞에 붙은 멤버는 오직 그 정의를 포함한 클래스가 객체 내부에서만 접근할 수 있다. 이 규칙을 inner class 에도 똑같이 적용한다.

```scala
class Outer {
  class Inner {
    private def f() = println("f")

    class InnerMost {
      f() // 문제 없음
    }
  }

  (new Inner).f() // 접근 불가
}
```

### 보호 멤버

- protected 멤버에 대한 접근은 자바보다 약간 더 제한적
- 스칼라에서는 보호 멤버를 정의한 클래스의 서브클래스에서만 그 멤버에 접근할 수 있다. (자바에서는 어떤 클래스의 보호 멤버에 그 클래스와 같은 패키지 안에 있는 다른 클래스들도 접근할 수 있다.)

```scala
package p {
  class Super {
    protected def f() = {
      println("f")
    }
  }

  class Sub extends Super {
    f()
  }

  class Other {
    (new Super).f() // 접근 불가
  }
}
```

## 공개 멤버

- private나 protected가 없는 멤버는 모두 공개 멤버다.

## 보호 스코프

- 스칼라에서는 접근 수식자의 의미를 지정자로 확장할 수 있다.
- private[X]나 protected[X] 형태인 지정자는 접근이 X까지 비공개이거나 보호라는 뜻이다.
- 여기서 X는 그 접근 수식자를 둘러싸고 있는 패키지나 클래스 또는 싱글톤 객체를 가리킨다.

```scala
package bobsrockets
package navigation {
  private[bobsrockets] class Navigator {
    // Navigator의 서브클래스에서 모두 접근 가능하고, 더불어 navigation 패키지에 있는 모든 코드 안에서 접근 가능
    protected[navigation] def useStarChart() = {}

    class LegOfJourney {
      // LegOfJourney안에서와 Navigator 클래스 안에서 모두 접근 가능
      private[Navigator] val distance = 100
    }

    // 그 정의를 포함한 객체 내부에서만 접근 가능
    private[this] var speed = 200
  }
}

package launch {

  import navigation._

  object Vehicle {
    private[launch] val guide = new Navigator
  }

}
```

## 가시성과 동반 객체

- 스칼라 접근 규칙은 비공개(private) 또는 보호 접근(protected)에 대해 동반 객체와 클래스에 동일한 권리를 준다.
- 객체는 자신의 동반 클래스와 모든 접근권리를 공유하며, 또한 역도 마찬가지다.

```scala
class Rocket {

  import Rocket.fuel

  private def canGoHomeAgain = fuel > 20
}

object Rocket {
  private def fuel = 10
}

```

## 패키지 객체

- 클래스 내부에 둘 수 있는 어떤 정의든 패키지의 최상위 수준에 둘 수 있다. 패키지 전체 스코프에 헬퍼 메서드를 두고 싶다면 그냥 패키지 최상위 수준에 넣으면 된다.
- `package object 패키지이름`
- 미리 이야기하자면 패키지 객체를 사용하는 다른 용도가 있다. 패키지 내에서 사용할 타입 별명과 암시적 변환을 넣기 위해 패키지 객체를 쓰는 경우가 많다. (20장, 21장)

```scala

package object bobsdelights {
  def showFruit(fruit: Fruit) = {
    import fruit._
    println(name + "s are" + color)
  }
}
```
