# upbit-api-with-telegram
upbit api with telegram bot


# Exchange API

## Deposits

삽질기 -> APIKEY발급시 권한 설정에 따라 호출이 되고 안된다...이번 입금테스트를 위해 기존 API삭제하고 모든 권한을 가진 APIKEY발급.....

권한 설정에 따라 접근 권한이 달라지기 때문에 무려 2시간을 삽질.....

입금 조회 리스트/개별 입금 조회 API 작성 및 테스트 완료

# Next
현재 서비스 레벨의 로직의 경우 반복되는 코드가 많다. 예를 들면 webclient로 요청하는 부분은 공통 서비스로 빼서 코드를 간결하게 하는 것이 차후 목표이다.
(지금은 복붙복...귀차니즘...)

Post관련 로직 수정해야함. RequestParam이 아닌 BodyParam으로 넘어가게 때문이다....;;;