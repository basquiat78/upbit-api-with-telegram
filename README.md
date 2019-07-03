# upbit-api-with-telegram
upbit api with telegram bot


# Exchange API

## Accounts
 
업비트 access key와 screte key를 이용해 jwt토큰을 생성하고 헤더에 담아서 업비트에 요청하는 API와 외부 API로 컨트롤러를 통해서 헤더에 담아 처리하는 컨트롤러 추가

## Order

### OrderChance

주문 가능 정보 조회 API 완료.

See http://localhost:8080/swagger-ui.html


업비트의 인증 가능한 요청 만들기의 스펙에 의거해 만듬.

CryptoUtils 추가
JwtUtils 수정

## Order And Order List

주문하기 와 주문 리스트 조회 API작성

주문하기는 테스트 못함 (상상으로...)

주문 리스트 조회는 테스트 완


# Add

[See UpbitApiUsingHttpURLConnection.java](https://github.com/basquiat78/upbit-api-with-telegram/blob/exchange-api-v0.1/src/test/java/io/basquiat/UpbitApiUsingHttpURLConnection.java)

스프링 부트의 웹플럭스를 사용하는 것이 아니라면 RestTemple이나 Retrofit2같은 라이브러리를 활용할 수 있다.
위 방법은 심플하게 HttpURLConnection을 이용한 방법이다.

# 업비트 API 변경 공지사항

[Open API 변경사항 안내 (적용 일자 07/04 15:00)
](https://docs.upbit.com/changelog/open-api-%EB%B3%80%EA%B2%BD%EC%82%AC%ED%95%AD-%EC%95%88%EB%82%B4-%EC%A0%81%EC%9A%A9-%EC%9D%BC%EC%9E%90-0704-1500)

# Next
참 이럴 때는 NodeJs가 확실히 편하다.
업비트에서도 관련 API 예제는 자바만 쏙 빼놓았다. 하긴 귀찮긴 하다.....

jUnit 테스트시 발생하는 텔레그램 BotSession was bound관련 에러는 수정.

하지만 spring-boot-devtools로 리소스 수정후 서버가 다시 뜰 때 발생하는 Error getting updates관련 에러는 지금 해결점을 찾고 있는 중...

-> 해결 방법은 찾았지만 너무 느려서 주석만 처리함. 해당 라이브러리 깃헙 이슈에는 버그로 규정하고 있는데 아직 수정되지 않은 듯...
   차라리 그냥 서버를 껐다 키는게 더 빠를 듯...

# Telegram Usage

![실행이미지](https://github.com/basquiat78/upbit-api-with-telegram/blob/quotation-api-v0.1/capture/capture7.png)

위 그림처럼 //accounts 커맨드를 날리면 자신의 자산을 json 데이터로 봇을 통해 메세지를 보낸다.

토큰을 생성하는 방식은 [io.basquiat.common.util.JwtUtils](https://github.com/basquiat78/upbit-api-with-telegram/blob/exchange-api-v0.1/src/main/java/io/basquiat/common/util/JwtUtils.java)을 통해서 확인할 수 있다.

업비트에서는 base64로 인코딩하지 않기 때문에 만일 다른 거래소를 위해서 사용하기 위해선 base64로 인코딩하는 로직이 추가되어야 한다.
이 프로젝트는 업비트를 기준으로 한다.

# Add Telegram Send Message API
스웨거 참조