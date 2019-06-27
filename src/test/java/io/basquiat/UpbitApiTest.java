package io.basquiat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import io.basquiat.schedule.service.MarketScheduleService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UpbitApiTest {
	
	@Autowired
	private MarketScheduleService quotationScheduleService;
	
	@Test
	public void scheduleDurationChangeTest() throws InterruptedException {
		quotationScheduleService.start();
        Thread.sleep(20000);
        quotationScheduleService.changeCronExpression("*/2 * * * * *");
        Thread.sleep(20000);
	}

}
