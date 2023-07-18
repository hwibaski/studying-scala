# 스칼라의 계층 구조

스칼라의 모든 클래스는 공통의 슈퍼클래스 Any를 상속한다.

## 스칼라 클래스의 계층 구조

### Any

- 최상위에는 Any
    - 아래의 메서들을 가지고 있다.
    - `final def ==(that: Any): Boolean`
    - `final def !=(that: Any): Boolean`
    - `final def equals(that: Any): Boolean`
    - `def ##: Int`
        - 해시값 반환
    - `def hasCode: Int`
        - 해시값 반환
    - `def toString: String`

### AnyVal

- 스칼라값 클래스의 부모 클래스
- Byte, Short, Char, Int, Long, Float, Double, Boolean, Unit
- Unit을 제외한 나머지 타입은 자바의 원시타입에 대응, 실행 시점에 자바의 원시 타입 값으로 표현
- 42는 Int의 인스턴스이고, 'x'는 Char의 인스턴스이다.
- 모든 값 클래스는 추상 클래스인 동시에 final 클래스이기 때문에 인스턴스화 할 수 없다.

```scala
42 max 43
42 min 43
1 until 5
1 to 5
3.abs
(-3).abs
```

위의 정의들은 모두 scala.runtime.RichInt에 있다. Int에는 없는 메서드들이다. Int값에 해당 메서드들을 호출하면 암시적 변환을 통해 해당 메서드들을 적용한다.

### AnyRef

- 스칼라의 모든 참조 클래스의 기반 클래스
- 자바로 작성한 클래스나 스칼라로 작성한 클래스는 모두 AnyRef를 상속

```text
boolean isEqual(int x, int y) {
    return x == y;
}
isEqual(421, 422) // true

boolean isEqual(Integer x, Integer y) {
    return x == y;
}
isEqual(421, 422) // false
// 421의 값을 파라미터마다 각각 박싱하기 때문에 서로 다른 객체가 되고 자바의 == 는 참조 동일성일 확인하므로 false가 나온다.
```

```scala
def isEqual(x: Int, y: Int) = x == y

isEqual(421, 421) // true

def isEqual(x: Any, y: Any) = x == y

isEqual(421, 421) // true

// 스칼라의 == 연산은 타입의 표현과 관계없이 투명학 ㅔ동작한다.

val x = "abcd".substring(2)
val y = "abcd".substring(2)

x == y // true

// 참조 동일성이 필요한 경우에는 eq와 eq를 뒤집은 ne를 사용하면 된다.

val x = new String("abc")
val y = new String("abc")

x == y // true
x eq y // false
x ne y // true
```

### 바닥에 있는 타입

- Nothing
    - 스클라의 클래스 계층의 가장 밑바닥에 존재한다.
    - 모든 타입의 서브타입이다.
    - 가장 유용할 때는 비정상적 종료를 표시하는 것이다.
- Null
    - AnyRef를 상속한 모든 클래스의 서브클래스다.
    - Null은 값 타입과는 호환성이 없다. val i = Int = null <- 이 코드는 컴파일 되지 않는다.

```scala
def divide(x: Int, y: Int): Int =
  if (y != 0) x / y
  else sys.error("can't divide by zero") // Nothing 타입 반환

// Nothing은 Int 타입의 서브타입이기 때문에 Nothing을 반환할 수 있다.
```
