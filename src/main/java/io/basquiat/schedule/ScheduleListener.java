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
	 * app이 뜰 때 초기화 값을 세팅하고 스케쥴 스타.
	 */
	@EventListener(ApplicationReadyEvent.class)
	public void startApp() {
		marketService.setUpMarketAllStore();
		marketScheduleService.start();
	}
	
}
