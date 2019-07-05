# upbit-api-with-telegram
upbit api with telegram bot


# Exchange API

## Withdraws

개별 출금과 출금 리스트 조회 작성 완료

1. 일단 출금한 기록이 전혀 없어서 출금 리스트 조회시 빈 배열이 넘어오는 것은 확인
2. 하지만 개별 출금시에 uuid 또는 txid가 없으면 bad request 400 에러 발생...

이와 같은 형상은 Order API, 즉 이와 비슷한 주문 리스트 조회와 개별 주문도 같은 현상이 나타남
주문 리스트를 먼저 조회하고 그중 하나의 uuid로 개별 조회하면 잘 나옴.
하지만 유효하지 않은 uuid로 하면 400에러 발생...

곰곰히 생각해 보면 저 둘의 공통점은 Mono라는 것이다. 배열의 경우 없으면 빈 배열을 보내주면 되지만 싱글인 경우에는 저렇게 처리한듯?? (그냥 예상)

출금 가능 정보 작성 완료 O

코인/원화 출금하기 작성 완료 O, 테스트는 못함 (안한것..할수 없음)

# Next
현재 서비스 레벨의 로직의 경우 반복되는 코드가 많다. 예를 들면 webclient로 요청하는 부분은 공통 서비스로 빼서 코드를 간결하게 하는 것이 차후 목표이다.
(지금은 복붙복...귀차니즘...)

# Add JWT Generate Controller
jwt 생성 요청 컨트롤러 추가