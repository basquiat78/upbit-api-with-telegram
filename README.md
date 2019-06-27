# upbit-api-with-telegram
upbit api with telegram bot


# Exchange API

Accounts

See http://localhost:8080/swagger-ui.html
 
업비트 access key와 screte key를 이용해 jwt토큰을 생성하고 헤더에 담아서 업비트에 요청하는 API와 외부 API로 컨트롤러를 통해서 헤더에 담아 처리하는 컨트롤러 추가

# Telegram Usage

![실행이미지](https://github.com/basquiat78/upbit-api-with-telegram/blob/quotation-api-v0.1/capture/capture7.png)

위 그림처럼 //accounts 커맨드를 날리면 자신의 자산을 json 데이터로 봇을 통해 메세지를 보낸다.

토큰을 생성하는 방식은 [io.basquiat.common.util.JwtUtils]()을 통해서 확인할 수 있다.

업비트에서는 base64로 인코딩하지 않기 때문에 만일 다른 거래소를 위해서 사용하기 위해선 base64로 인코딩하는 로직이 추가되어야 한다.
이 프로젝트는 업비트를 기준으로 한다.