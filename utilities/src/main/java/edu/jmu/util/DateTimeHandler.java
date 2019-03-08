package edu.jmu.util;

import org.springframework.util.StringUtils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * @author sun
 * @date 2018/12/17
 */
public class DateTimeHandler {
    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    /**
     * Long时间戳时间转localDate
     * @param time
     * @return
     */
    public static LocalDate timestampToLocalDate(Long time) {
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(time), ZoneId.of("Asia/Shanghai")).toLocalDate();
    }

    public static LocalDate stringToLocalDate(String time){
        return LocalDate.parse(time,dateTimeFormatter);
    }

    /**
     * LocalDate转Long时间戳
     * @param localDate
     * @return
     */
    public static Long localDateToTimestamp(LocalDate localDate){
        return localDate.atTime(0,0,0).atZone(ZoneId.of("Asia/Shanghai")).toInstant().getEpochSecond();
    }

    /**
     * String时间转Long时间戳
     * @param date
     * @return
     */
    public static Long stringToTimestamp(String date){
        if (StringUtils.isEmpty(date)){
            return null;
        }
        return LocalDate.parse(date).atTime(0,0,0).atZone(ZoneId.of("Asia/Shanghai")).toInstant().getEpochSecond();
    }

    /**
     * 获取当天凌晨0点的Instant
     * @return
     */
    public static Instant getEarlyMorningInstant(){
        //获取当天时间
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        //把时间清零，获取到凌晨时间
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.toInstant();
    }
}
