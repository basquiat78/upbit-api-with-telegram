package io.basquiat.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import io.basquiat.quotation.service.MarketService;
import io.basquiat.schedule.service.MarketScheduleService;

/**
 * 
 * Schedule Listener
 * 
 * created by basquiat
 *
 */
@Component
public class ScheduleListener {

	@Autowired
	private MarketService marketService;
	
	@Autowired
	private MarketScheduleService marketScheduleService;
	
	/**
	 * app이 뜰 때 초기화 값을 세팅한다.
	 */
	@EventListener(ApplicationReadyEvent.class)
	public void startApp() {
		marketScheduleService.start();
	}
	
	/**
	 * app이 뜰 때 스케쥴을 구동시킨다.
	 */
	@EventListener(ApplicationReadyEvent.class)
	public void quotationScheduling() {
		marketService.setUpMarketAllStore();
	}
	
}
