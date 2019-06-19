package io.basquiat.schedule.service;

import java.util.concurrent.ScheduledFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import io.basquiat.quotation.service.MarketService;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * created by basquiat
 * 
 * 스케쥴 서비스. 시세를 가져오는 주기를 변경할 수 있는 서비
 *
 */
@Slf4j
@Service("marketScheduleService")
public class MarketScheduleService {

	@Value("${schedule.market.cron}")
	private String MARKET_CRON_EXPRESSION;
	
	@Autowired
	private MarketService marketService;
	
	@Autowired
	private TaskScheduler marketScheduler;
	
	private ScheduledFuture<?> marketScheduledFuture;

	/**
	 * schedule start
	 */
    public void start() {

    	ScheduledFuture<?> marketScheduledFuture = this.marketScheduler.schedule(() -> {
    																marketService.setUpMarketAllStore();
							                					  },
							                					new CronTrigger(MARKET_CRON_EXPRESSION)
						                					);
        this.marketScheduledFuture = marketScheduledFuture;
    }

    /**
     * 스케쥴링 주기를 변경한다.
     * 1. 기존의 돌고 스케쥴을 멈춘다.
     * 2. 넘어온 크론으로 다시 세팅한다.
     * 3. 다시 스케쥴을 구동시킨다.
     * 
     * @param cron
     */
    public void changeCronExpression(String cron) {
    	if(marketScheduledFuture != null) {
    		marketScheduledFuture.cancel(true);
    	}

        this.marketScheduledFuture = null;
        log.info("market schedule cron expression '" + this.MARKET_CRON_EXPRESSION + "' change to '" + cron + "'");
        this.MARKET_CRON_EXPRESSION = cron;
        this.start();
    }
    
}
