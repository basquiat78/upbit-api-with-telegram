package io.basquiat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import io.basquiat.common.code.ExchangeApiUri;
import io.basquiat.common.util.JwtUtils;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UpbitApiUsingHttpURLConnection {
	
	@Value("${upbit.api.url}")
	private String UPBIT_API_URL;
	
	@Value("${upbit.api.version}")
	private String UPBIT_API_VERSION;
	
	@Value("${upbit.access.key}")
	private String UPBIT_ACCESS_KEY;
	
	@Value("${upbit.secret.key}")
	private String UPBIT_SECRET_KEY;
	
	@Test
	public void apiCallUsingHttpURLConnectionTest() {

		String jwt = JwtUtils.createJwtWithoutQueryParameters(UPBIT_ACCESS_KEY, UPBIT_SECRET_KEY);
        try {
            String apiURL = UPBIT_API_URL + UPBIT_API_VERSION + ExchangeApiUri.ACCOUNTS.URI;
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Authorization", jwt);
            int responseCode = con.getResponseCode();
            
            // 헤더 정보 체
            for(int i=0; i<con.getHeaderFields().size() ;i++) {
            	System.out.println(con.getHeaderField(i));
            	
            }
            
            // 요청 제한 정보 체크, 호출된 URI찍어보자ㅏ
            System.out.println(con.getHeaderFields().get("Remaining-Req").get(0));
            System.out.println(con.getHeaderFields().get("X-Forwarded-Uri")  );
            
            BufferedReader br;
            if(responseCode==200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {  // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            // 이 방법을 사용하게 되면 Account도메인을 사용할 수 없다.
            // 이유는 해당 도메인은 롬복의 @Value을 이용해 Immutable하게 설정했기 때문이다.
            System.out.println(response.toString());
        } catch (Exception e) {
            System.out.println(e);
        }
		
	}
	
}
