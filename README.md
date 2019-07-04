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


# Add JWT Generate Controller
jwt 생성 요청 컨트롤러 추가