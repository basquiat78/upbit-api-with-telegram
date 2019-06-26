package io.basquiat.quotation.domain;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

import io.basquiat.quotation.domain.response.market.MarketAll;

/**
 * 
 * 거래가능한 마켓 정보를 메모리에 올린다.
 * 
 * created by basquiat
 *
 */
public class MarketAllStore {

	private static final Map<String, MarketAll> MARKETALL_STORE = new ConcurrentHashMap<>();
	
	/**
	 * get MarketAll
	 * @return Map<String, MarketAll>
	 */
	public static Map<String, MarketAll> getMarketAllStore() {
		return MARKETALL_STORE;
	}
	
	public static Collection<MarketAll> getMarketAllList() {
		return MARKETALL_STORE.values();
	}
	
	/**
	 * change MarkeAll Store
	 * @param transactionList
	 */
	public static void onMarketAllStore(List<MarketAll> marketAllList) {
		MARKETALL_STORE.clear();
		Map<String, MarketAll> changedMap = marketAllList.stream()
													  	 .collect(Collectors.toMap(MarketAll::getMarket, Function.identity()));
		MARKETALL_STORE.putAll(changedMap);
	}

}
