package _03

import scala.collection.mutable

object Main extends App {
  val acc = new ChecksumAccumulator
  val csa = new ChecksumAccumulator

  // acc = new ChecksumAccumulator 컴파일 불가, acc가 val 이기 때문에

  acc.sum = 3
  // acc.sum2   컴파일 불가 : private 필드이기 때문에

  ChecksumAccumulatorV2.calcualte("Every value is an object.")
  // object ChecksumAccumulatorV2와 class ChecksumAccumulatorV2는 이름이 서로 같다.
  // 어떤 싱글톤 객체의 이름이 어떤 클래스와 같을 때, 그 객체를 클래스의 동반 객체라고 한다.
  // 역으로 해당 클래스는 싱글톤 객체의 동반 클래스라고 부른다.
  // 다만, 클래스와 객체는 반드시 같은 소스 파일 내에 정의해야 한다.
}

// 멤버를 public하게 하려면 어떤 접근 수식자도 지정하지 않아야 한다.
class ChecksumAccumulator {
  var sum = 0
  private var sum2 = 0
}

class ChecksumAccumulatorV2 {
  private var sum = 0

  // 스칼라에서 선호하는 코드 형태로 변경 전
  // 메서드의 파라미터는 val이다
  // 명시적으로 return 하지 않아도 return 한다.
  //  def add(b: Byte): Unit = {
//    return sum += b
//  }

  // 변경 후
  // add의 경우 부수 효과는 sum을 재할당하는 것이다. 부수 효과만을 위해 실행되는 메서드를 프로시저라고 부른다.
  def add(b: Byte): Unit = sum += b

  def checksum(): Int = ~(sum & 0xff) + 1
}

object ChecksumAccumulatorV2 {
  private val cache = mutable.Map.empty[String, Int]

  def calcualte(s: String): Int = {
    // cache Map에 해당 문자열이 포함되어 있으면
    if (cache.contains(s)) {
      // 캐시에서 해당 값 찾아서 리턴
      cache(s) // 굳이 자바로 따지자면 map.get("key")
    } else { // 캐시에 없으면
      // Checksum 누산기 새로운 인스턴스 생성
      val acc = new ChecksumAccumulatorV2

      // 문자열이 각 캐릭터들을 순회하면서
      for (c <- s) {
        // 누산기에 캐릭터들을 바이트로 변환해서 add메서드 호출
        acc.add(c.toByte)
      }
      // 누산기에서 최종 checksum 계산에서 리턴
      val cs = acc.checksum()

      // 캐시맵에 인자로 받았던 문자열을 키값으로 하는 체크섬 저장, map.put(s, cs) 정도로 볼 수 있겠다.
      cache += (s -> cs)

      // 그 과정에서 계산한 checksum은 리턴
      cs
    }
  }

}
