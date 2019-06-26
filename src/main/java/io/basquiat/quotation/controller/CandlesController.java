package io.basquiat.quotation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.basquiat.quotation.domain.response.candles.Days;
import io.basquiat.quotation.domain.response.candles.Minutes;
import io.basquiat.quotation.domain.response.candles.WeeksAndMonths;
import io.basquiat.quotation.service.CandlesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import reactor.core.publisher.Flux;

/**
 * 
 * Functional Route방식이 아닌 Rest방식으로 작성한다.
 * 
 * created by basquiat
 *
 */
@RestController
@RequestMapping("/api/candles")
@Api(value = "Candles Controller", tags = {"Upbit Chart Data Controller"})
public class CandlesController {

	@Autowired
	private CandlesService candlesService;
	
	/**
	 * 분봉 캔들 차트
	 * @param unit
	 * @param market
	 * @param to
	 * @param count
	 * @return Flux<Minutes>
	 */
	@ApiOperation(value = "분(Minute) 캔들 차트 데이터")
	@GetMapping("/minutes/{unit}")
	public Flux<Minutes> minutesCandle(@PathVariable("unit") int unit,
									   @RequestParam(name = "market", required = true) String market,
									   @RequestParam(name = "to", required = false) String to,
									   @RequestParam(name = "count", required = false, defaultValue = "1") int count) {
		return candlesService.getMinutesOfCandles(unit, market, to, count);
	}
	
	/**
	 * 일봉 캔들 차트
	 * @param market
	 * @param to
	 * @param count
	 * @param convertingPriceUnit
	 * @return Flux<Days>
	 */
	@ApiOperation(value = "일(Day) 캔들 차트 데이터")
	@GetMapping("/days")
	public Flux<Days> daysCandle(@RequestParam(name = "market", required = true) String market,
							   	 @RequestParam(name = "to", required = false) String to,
							   	 @RequestParam(name = "count", required = false, defaultValue = "1") int count,
							   	 @RequestParam(name = "convertingPriceUnit", required = false) String convertingPriceUnit) {
		return candlesService.getDaysOfCandles(market, to, count, convertingPriceUnit);	
	}
	
	/**
	 * 주봉 캔들 차트
	 * @param market
	 * @param to
	 * @param count
	 * @return Flux<WeeksAndMonths>
	 */
	@ApiOperation(value = "주(Week) 캔들 차트 데이터")
	@GetMapping("/weeks")
	public Flux<WeeksAndMonths> weeksCandle(@RequestParam(name = "market", required = true) String market,
									   	 	@RequestParam(name = "to", required = false) String to,
									   	 	@RequestParam(name = "count", required = false, defaultValue = "1") int count) {
		return candlesService.getWeeksOfCandles(market, to, count);	
	}
	
	/**
	 * 월봉 캔들 차트
	 * @param market
	 * @param to
	 * @param count
	 * @return Flux<WeeksAndMonths>
	 */
	@ApiOperation(value = "월(Month) 캔들 차트 데이터")
	@GetMapping("/months")
	public Flux<WeeksAndMonths> monthsCandle(@RequestParam(name = "market", required = true) String market,
										   	 @RequestParam(name = "to", required = false) String to,
										   	 @RequestParam(name = "count", required = false, defaultValue = "1") int count) {
		return candlesService.getMonthsOfCandles(market, to, count);	
	}
	
}
