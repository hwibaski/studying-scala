## 스칼라 두 번째 걸음

## List 메서드와 사용법

| 형태                                                                   | 설명                                                                                                  |
|----------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------|
| List() , Nil                                                         | 빈 리스트                                                                                               |
| List("Cool", "tools", "rule")                                        | Cool, tools, rule로 이루어진 리스트 반환                                                                      |
| val thrill = "Will" :: "fill" :: "until" :: Nil                      | Will, fill, until로 이루어진 리스트 반환                                                                      |
| List("a", "b") ::: List("c", "d")                                    | 두 개의 리스트 병합해서 새로운 리스트 반환                                                                            |
| thrill(2)                                                            | 리스트의 2 인덱스 요소 반환                                                                                    |
| thrill.count(s => s.length == 4)                                     | 리스트에서 문자열 길이가 4인 요소의 개수 반환                                                                          |
| thrill.drop(2)                                                       | 리스트에서 처음 두 원소를 없앤 새로운 리스트 반환 <br/> 여기서는 List("until") 반환                                            |
| thrill.dropRight(2)                                                  | 리스트에서 맨 오른쪽의 두 원소를 없앤 새로운 리스트를 반환<br/>여기서는 List("Will") 반환                                          |
| thrill.exists(s => s =="until")                                      | 리스트에서 해당 조건을 가진 요소가 있는지 여부 반환                                                                       |
| thrill.filter(s => s.length == 4)                                    | 리스트에서 해당 조건을 충족하는 요소를 반환                                                                            |
| thrill.filterNot(s => s.length == 4)                                 | 리스트의 요소 중 해당 조건을 충족하지 않는 요소를 반환                                                                     |
| thrill.forall(s => s.endsWith("I"))                                  | 리스트의 모든 요소가 조건을 충족하는지 여부 반환                                                                         |
| thrill.foreach(s => print(s))                                        | 리스트의 요소 순회                                                                                          |
| thrill.foreach(print)                                                | 바로 위와 같음                                                                                            |
| thrill.head                                                          | 리스트의 첫 번째 요소 반환                                                                                     |
| thrill.last                                                          | 리스트의 마지막 요소 반환                                                                                      |
| thrill.isEmpty                                                       | 리스트가 비어 있는지 여부 반환                                                                                   |
| thrill.init                                                          | 리스트에서 마지막 원소를 제외한 나머지 리스트 반환                                                                        |
| thrill.tail                                                          | 리스트에서 첫 원소를 제외한 나머지 리스트 반환                                                                          |
| thrill.length                                                        | 리스트의 길이 반환                                                                                          |
| thrill.map(s => s + "y")                                             | 리스트의 원소에 추가적인 작업을 한 후 새로운 리스트 반환                                                                    |
| thrill.mkString(", ")                                                | 리스트의 원소를 가지고 문자열을 만든다. "Will, fill, until" 반환                                                       |
| thrill.reverse                                                       | 리스트를 역순으로 담은 리스트 반환                                                                                 |
| thrill.sortWith((s, t) => s.charAt(0).toLower < t.charAt(0).toLower) | 리스트를 알파벳 순서대로 정렬하되, 원소 문자열의 첫 글자를 소문자로 만들어서 비교한<br/>정렬한 새로운 리스트를 반환 <br/>List(fill, until, Will) 반환 |

