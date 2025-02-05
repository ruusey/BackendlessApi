package com.util;

import java.time.ZonedDateTime;
import java.util.List;

import org.joda.time.format.ISODateTimeFormat;
import org.ta4j.core.BaseTimeSeries;
import org.ta4j.core.TimeSeries;
import org.ta4j.core.Trade;
import org.ta4j.core.TradingRecord;

import com.models.Candle;

public class AnalysisUtil {
	public static TimeSeries candleListToTimeseries(List<Candle> candles, String name){
		System.out.println("Creating timeseries from "+candles.size()+" candles");
		TimeSeries series = new BaseTimeSeries.SeriesBuilder().withName(name).build();
		candles.forEach(candle->{
			String parsedDate = AnalysisUtil.epochToUTCString(candle.getDatetime());
			ZonedDateTime time = ZonedDateTime.parse(parsedDate);
			series.addBar(time,candle.getOpen(),candle.getHigh(),candle.getLow(),candle.getClose(),candle.getVolume());
		});
		return series;
	}
	public static String epochToUTCString(long epoch) {
		org.joda.time.format.DateTimeFormatter fmt = ISODateTimeFormat.dateTime();
		return fmt.print(epoch);
	}
	public static void printTradingRecord(TradingRecord records) {
		
		records.getTrades().forEach(trade->{
			System.out.println(trade.getEntry().getType()+" "+trade.getEntry().getPrice());
			if(trade.getExit()==null) return;
			System.out.println(trade.getExit().getType()+" "+trade.getExit().getPrice());
		});
	}
}
