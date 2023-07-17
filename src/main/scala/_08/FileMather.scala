package _08

object FileMather {
  private def filesHere = (new java.io.File(".")).listFiles

  def filesEndingV1(query: String) =
    for (file <- filesHere; if file.getName.endsWith(query))
      yield file

  def filesContainingV1(query: String) =
    for (file <- filesHere; if file.getName.contains(query))
      yield file

  def filesRegexV1(query: String) =
    for (file <- filesHere; if file.getName.matches(query))
      yield file

  def filesMatching(query: String, matcher: (String, String) => Boolean) = {
    for (file <- filesHere; if matcher(file.getName, query))
      yield file
  }

  // 함수 본문에서 각 인자를 한 번씨만 사용하고, 첫 번째 인자를 본문에서도 맨 처음 사용하고,두 번째 인자도 본문에서 두 번째로 사용하기 때문에
  // _.endsWith(_) 을 사용 가능, 첫번째 _ : fileName, 두번째 _ : query
  // (fileName: String, query: String) => fileName.endsWith(query)
  def filesEndingV2(query: String) =
    filesMatching(query, _.endsWith(_))

  def filesContainingV2(query: String) =
    filesMatching(query, _.contains(_))

  def fileRegexV2(query: String) =
    filesMatching(query, _.matches(_))
  
  // 클로저를 이용하여 코드 개선
  def filesMatchingV2(matcher: String => Boolean) = {
    for (file <- filesHere; if matcher(file.getName))
      yield file
  }

  def filesEndingV3(query: String) =
    filesMatchingV2(_.endsWith(query))

  def filesContainingV3(query: String) =
    filesMatchingV2(_.contains(query))

  def fileRegexV3(query: String) =
    filesMatchingV2(_.matches(query))
}
