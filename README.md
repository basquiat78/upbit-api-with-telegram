# upbit-api-with-telegram
upbit api with telegram bot


# 일단 Telegram bot을 생성하자.
## 1. 텔레그램 가입
  - 따로 설명하지 않겠다.    
    https://telegram.org/    
    
## 2. Telegram Bot 생성
텔레그램 화면 상당에 보면 Search영역이 있다. BotFather라고 입력을 하면 다음과 같은 화면을 볼 수 있다.    
![실행이미지](https://github.com/basquiat78/upbit-api-with-telegram/blob/quotation-api-v0.1/capture/capture1.png)

클릭해서 대화창으로 들어가서 '/start'을 입력하고 엔터를 누른다.    
![실행이미지](https://github.com/basquiat78/upbit-api-with-telegram/blob/quotation-api-v0.1/capture/capture2.png)


위의 이미지에 대해서 설명하자면 '/start'를 입력하면 BotFather가 Help와 관련된 내용에 대해서 답을 준다.

'/newbot'을 입력하자.    

그러면 첫 번째 질문을 하게 된다.    

'Alright, a new bot. How are we going to call it? Please choose a name for your bot.'    
이 문구가 뜨면 봇의 이름을 짓는다. 참고로 이때 입력하는 이름은 채팅 목록에서 드러나게 되는 이름이 되겠다.


나는 Basquiat라고 쳤다. 자신이 원하는 이름을 지어보자. 중복되는 경우에는 아마 대답을 해줄거 같은데 나의 경우에는 그렇지 않아서..다른 분들 글을 참고해보면 될거 같다.

이름을 지었으면 BotFather가 다시 한번 물어본다.    

'Good. Now let's choose a username for your bot. It must end in `bot`. Like this, for example: TetrisBot or tetris_bot.'    

나의 경우에는 Basquiat_bot이라고 했다. 중복된게 없어서 바로 통과!

이후 BotFather가 대답을 할 때는 텔레그램의 뻘건 글씨로 된 API_TOKEN을 알려준다.

형식은 대충 '1234566:abcdefg123tygg...' <-- 요렇게 구분자 ':'를 두고 생겨먹은 넘이다.

텔레그램과 관련된 라이브러리중에는 텔레그램 채널 아이디가 필요한 경우도 있는데 나의 경우에는 chatId만 알면된다.


## 3. chat Id는 어떻게    

알 수 있는 방법은 두가지다. 하나는 로직 상에서 찍어보는 방법이다.

텔레그램 API에서 제공하는 TelegramLongPollingBot.java의 스펙중 

``` 
@Override
	public void onUpdateReceived(Update update) {
		// TODO
	}
```

에서 Update객체에서 update.getMessage().getChatId()로 얻어올 수 있다.

또 하나는 Webhook으로 알아 볼 수 있다.

https://api.telegram.org/bot{your_api_token}/getUpdates

이 주소를 날리기 전에 봇 채팅방으로 들어가서 /start를 눌러주고 아무 말이나 입력하고 위 주소로 다시 조회해 보자.    


``` 
{
	ok: true,
	result: [{
		update_id: 385095922,
		message: {
			message_id: 341,
			from: {
				id: 354942933,
				is_bot: false,
				first_name: "Basquiat",
				last_name: "Yoon",
				language_code: "ko"
			},
			chat: {
				id: your chat id, // <--- chat id here
				first_name: "Basquiat",
				last_name: "Yoon",
				type: "private"
			},
			date: 1560909136,
			text: "ddd"
		}
	}]
}
```

# Upbit API    

https://docs.upbit.com/v1.0.3/reference

이 사이트를 보면 총 2개의 카테고리의 API를 제공한다.

그 중에 EXCHANGE API는 실제 본인 계정의 자산, 매도, 매수, 출금, 입금에 대한 부분에 접근할 수 있기 때문에 accessKey, secretKey를 발급받는다.

하지만 QUOTATION API는 단순한 시세 관련 조회라 필요하지 않다.

만일 단순하게 시세 조회와 사용 가능한 마켓 조회만 한다면 api 발급 과정은 필요하지 않다.

하지만 이 프로젝트는 거래는 아니더라도 내 계정의 자산을 조회할 예정이기 때문에 나는 발급을 받았다.

발급 과정은 업비트 -> 고객 센터 -> Open API 안내 -> Open API 사용하기 를 클릭하면 된다.

업비트의 경우에는 발급 받을 때 해당 토큰에 대한 권한을 설정할 수 있다. 나는 나의 자산만 조회할 예정이라 계좌 조회만 체크하고 발급 받았다.

만일 자동매매프로그램을 염두해 두고 있다면 모든 것을 체크해야하며 특정 IP만 접근하도록 설정하는게 안전하다.    

# Usage    
생성한 봇 채팅방으로 들어간다.

![실행이미지](https://github.com/basquiat78/upbit-api-with-telegram/blob/quotation-api-v0.1/capture/capture3.png)    
1. //ticker [marketName]
커맨드는 //ticker 이고 구분자는 " ", 즉 공백을 넣고 뒤에 마켓명,예를 들면 BTC-XRP를 넣는다.

![실행이미지](https://github.com/basquiat78/upbit-api-with-telegram/blob/quotation-api-v0.1/capture/capture4.png)  
2. //ticker [marketName],[marketName] ...
여러개의 마켓 시세 조회시에는 구분자 ",", 즉 콤마로 여러개의 마켓명, 예를 들면 BTC-XRP,BTC-ETH처럼 넣는다.

## 오류 처리
![실행이미지](https://github.com/basquiat78/upbit-api-with-telegram/blob/quotation-api-v0.1/capture/capture5.png)   
1. 마켓명을 넣지 않을 경우

![실행이미지](https://github.com/basquiat78/upbit-api-with-telegram/blob/quotation-api-v0.1/capture/capture6.png)   
2. 마켓명중 하나가 잘못되거나 업비트 마켓에 없는 경우

# Setup

[See application.yml](https://github.com/basquiat78/upbit-api-with-telegram/blob/quotation-api-v0.1/src/main/resources/application.yml)

``` 
spring:
  profiles:
    active: local

---
spring:
  profiles: local

#logging
logging:
  file: logs/local-logging-file.log
  level:
    root: INFO
    org:
      springframework:
        batch: DEBUG
  pattern:
    # console에 찍히는 로그 형식 정의
    console: "%d{yyyy-MM-dd HH:mm:ss} - %msg%n"
    # console에서 로그는 위에 형식으로 찍히지만 파일로 씌여질 때 해당 패턴으로 찍는다. 
    file: "%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n"

# ScheduleConfiguration thread pool size setup
thread:
  pool:
    size: 4

# defaul schedule rate 5 second
schedule:
  market:
    cron: '*/60 * * * * *'
  ticker:
    cron: '*/5 * * * * *'
  
# upbit information
# upbit access key and secret key
upbit:
  api:
    url: https://api.upbit.com
    version: /v1
  access:
    key: <Your Upbit Access Key>
  secret:
    key: <Your Upbit Secret Key>

telegram:
  api:
    token: <Your Telegram Api Token>
  bot:
    name: <Your Telegram Bot Name>
  chat:
    id: <Your Telegram Bot Chat Id>
  
```


위 사전 준비에서 얻은 값들을 세팅한다.    
참고로 telegram.bot.name은 _bot 또는 _Bot이 붙은 유저이름이다.     
챗팅방의 인포를 확인하게 되면 이것인 username이라는 것을 알 수 있다.

또한 업비트 키와 관련해서 혹시 이런 작업을 공유하게 된다면 올리는 일이 없도록 하자.    
만일 이 키가 접근할 수 있는 권한이 거래,입출금까지 가지고 있다면 아마도 당신의 잔고는 다 털려버렸을 것이다...

# Next 

Quotation API관련 남아있는 API작성과 텔레봇으로 응답형 리플라이가 아닌 특정 메세지를 계속적으로 보내거나 원하는 메세지를 보내는 부분 추가
