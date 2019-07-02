package io.basquiat.telegram.action;

import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.telegram.telegrambots.meta.api.objects.Update;

import io.basquiat.common.code.ActionEnum;
import io.basquiat.common.code.DelimiterEnum;
import io.basquiat.common.util.CommonUtils;
import io.basquiat.exchange.service.AccountService;
import io.basquiat.quotation.service.TickerService;
import io.basquiat.telegram.service.TelegramService;

/**
 * 텔레그램으로 들어온 메세지를 통해서 reply를 하기 위한 객체
 * created by basquiat
 *
 */
public class BotAction {
	
	/**
	 * 
	 * 다음과 같은 방식으로 reply한다.
	 * 1. 시세 조회
	 *  -> //ticker BTC-EHT, BTC-XRP or BTC-EHT,BTC-XRP
	 * 
	 * @param update
	 * @param context
	 */
	public static void reply(Update update, ApplicationContext context) {
		TelegramService telegramService = (TelegramService) context.getBean(ActionEnum.TELEGRAM.serviceName);
		// 넘어온 메세지
		String fromMessage = update.getMessage().getText();
		if(fromMessage.contains(ActionEnum.TICKER.command)) { // 커맨드 체크. ticker 조회
			TickerService tickerService = (TickerService) context.getBean(ActionEnum.TICKER.serviceName);

			try {
				String marketNames = fromMessage.split(DelimiterEnum.SPACE.character)[1];
				
				Map<String, String> map = CommonUtils.validateMarketName(marketNames.split(DelimiterEnum.COMMA.character));
				
				if(map.containsKey(ActionEnum.INVALID.name())) { // 유효하지 않는 또는 잘못 입력한 마켓명을 텔레그램으로 메세지를 보낸다.
					telegramService.sendMessage(map.get(ActionEnum.INVALID.name()));
				}
				
				if(map.containsKey(ActionEnum.VALID.name())) {
					tickerService.getTicker(map.get(ActionEnum.VALID.name())).doOnError(e -> telegramService.sendMessage(e.getMessage() + " : Check Your Market Name"))
																		   	 .subscribe(tickerInfo -> {
																		   		 	telegramService.sendMessage(CommonUtils.convertJsonStringFromObject(tickerInfo));	
																		   	 });
				}
			} catch(ArrayIndexOutOfBoundsException e) {
				telegramService.sendMessage("마켓 정보가 없습니다.");
			}
			
		} else if(fromMessage.contains(ActionEnum.ACCOUNTS.command)) { // 커맨드 체크. 나의 자산 조회
			AccountService accountService = (AccountService) context.getBean(ActionEnum.ACCOUNTS.serviceName);
			accountService.getAccountsWithoutRequestHeader().doOnError(e -> telegramService.sendMessage(e.getMessage()))
													   	 	.subscribe(account -> {
													   	 			telegramService.sendMessage(CommonUtils.convertJsonStringFromObject(account));	
													   	 	});
		}

	}

}
