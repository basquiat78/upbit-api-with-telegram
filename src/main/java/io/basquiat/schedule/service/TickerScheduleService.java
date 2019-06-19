package io.basquiat.schedule.service;

import java.time.LocalDateTime;
import java.util.concurrent.ScheduledFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * created by basquiat
 * 
 * 스케쥴 서비스. 시세를 가져오는 주기를 변경할 수 있는 서비스
 *
 */
@Slf4j
@Service("tickerScheduleService")
public class TickerScheduleService {

	@Value("${schedule.ticker.cron}")
	private String TICKER_CRON_EXPRESSION;
	
	@Autowired
	private TaskScheduler tickerScheduler;
	
	private ScheduledFuture<?> tickerScheduledFuture;
	
	/**
	 * schedule start
	 */
    public void start() {

    	ScheduledFuture<?> tickerScheduledFuture = this.tickerScheduler.schedule(() -> {
                    												log.info("tickerScheduledFuture at " + LocalDateTime.now());

							                					  },
							                					new CronTrigger(TICKER_CRON_EXPRESSION)
						                					);
        this.tickerScheduledFuture = tickerScheduledFuture;
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

    	if(tickerScheduledFuture != null) {
    		tickerScheduledFuture.cancel(true);
    	}

        this.tickerScheduledFuture = null;
        this.TICKER_CRON_EXPRESSION = cron;
        log.info("ticker schedule cron duration time change");
        this.start();

    }
    
}
