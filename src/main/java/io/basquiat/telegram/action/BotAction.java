package io.basquiat.telegram.action;

import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.telegram.telegrambots.meta.api.objects.Update;

import io.basquiat.quotation.common.code.ActionEnum;
import io.basquiat.quotation.common.code.DelimiterEnum;
import io.basquiat.quotation.common.util.CommonUtils;
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
	 *  -> //ticker BTC-EHT,BTC-XRP
	 * 
	 * @param update
	 * @param context
	 */
	public static void reply(Update update, ApplicationContext context) {
		TelegramService service = (TelegramService) context.getBean(ActionEnum.TELEGRAM.serviceName);
		// 넘어온 메세지
		String fromMessage = update.getMessage().getText();
		if(fromMessage.contains(ActionEnum.TICKER.command)) { // 커맨드 체크.
			TickerService tickerService = (TickerService) context.getBean(ActionEnum.TICKER.serviceName);
			String marketNames = fromMessage.split(DelimiterEnum.SPACE.character)[1];
			
			Map<String, String> map = CommonUtils.validateMarketName(marketNames.split(DelimiterEnum.COMMA.character));
			
			if(map.containsKey(ActionEnum.INVALID.name())) { // 유효하지 않는 또는 잘못 입력한 마켓명을 텔레그램으로 메세지를 보낸다.
				service.sendMessage(map.get(ActionEnum.INVALID.name()));
			}
			
			if(map.containsKey(ActionEnum.VALID.name())) {
				tickerService.getTicker(map.get(ActionEnum.VALID.name())).doOnError(e -> service.sendMessage(e.getMessage() + " : Check Your Market Name"))
																	   	 .subscribe(tickerInfo -> {
																							service.sendMessage(CommonUtils.convertJsonStringFromObject(tickerInfo));	
																	   	 });
			}
		}
	}

}
