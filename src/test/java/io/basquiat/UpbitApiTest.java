package io.basquiat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec;

import io.basquiat.quotation.common.code.QuotationApiUri;
import io.basquiat.quotation.domain.response.MarketAll;
import io.basquiat.quotation.domain.response.Ticker;
import io.basquiat.schedule.service.MarketScheduleService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UpbitApiTest {
	
	@Value("${upbit.api.url}")
	private String UPBIT_API_URL;
	
	@Value("${upbit.api.version}")
	private String UPBIT_API_VERSION;
	
	@Autowired
	private MarketScheduleService quotationScheduleService;
	
	@Autowired
	private WebTestClient webTestClient;
	
	@Test
	public void tickerAPICallTest() {
		ResponseSpec responseSpec = webTestClient.get()
												 .uri(UPBIT_API_URL + UPBIT_API_VERSION + QuotationApiUri.TICKER.URI, "ETH-XRP")
												 .exchange()
												 .expectStatus()
												 .isOk();
		System.out.println(responseSpec.returnResult(Ticker.class));
	}
	
	@Test
	public void marketInfoAPICallTest() {
		
		ResponseSpec responseSpec = webTestClient.get()
												 .uri(UPBIT_API_URL + UPBIT_API_VERSION + QuotationApiUri.MARKET_ALL.URI)
												 .exchange()
												 .expectStatus()
												 .isOk();

		System.out.println(responseSpec.returnResult(MarketAll.class));
	}
	
	//@Test
	public void scheduleDurationChangeTest() throws InterruptedException {
		quotationScheduleService.start();
        Thread.sleep(20000);
        quotationScheduleService.changeCronExpression("*/2 * * * * *");
        Thread.sleep(20000);
	}

}
