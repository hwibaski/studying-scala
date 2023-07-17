package _09

/*
  ArrayElementV2의 파라미터에 있는 conts는 필드 중복을 피하기 위해 불필요한 코드를 생성한다.
  파라미터에 필드에서 초기할 값을 받으면 필드 코드를 따로 작성하지 않고 바로 초기화 가능하다.
  추가적인 기능은 Cat 클래스 참고.
 */
class ArrayElementV3(val contents: Array[String]) extends Element
