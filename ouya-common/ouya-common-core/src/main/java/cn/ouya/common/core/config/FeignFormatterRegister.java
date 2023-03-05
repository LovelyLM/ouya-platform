package cn.ouya.common.core.config;

import cn.hutool.core.date.DatePattern;
import org.springframework.cloud.openfeign.FeignFormatterRegistrar;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.Formatter;
import org.springframework.format.FormatterRegistry;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Feign格式化日期参数
 *
 * @author ：yuzhiheng
 * @date ：2021-08-11 10:56
 */
@Configuration
public class FeignFormatterRegister implements FeignFormatterRegistrar {

    @Override
    public void registerFormatters(FormatterRegistry registry) {
        registry.addFormatter(new LocalDateTimeFormatter());
        registry.addFormatter(new LocalDateFormatter());
        registry.addFormatter(new LocalTimeFormatter());
    }

    public static class LocalDateTimeFormatter implements Formatter<LocalDateTime> {

        @Override
        public LocalDateTime parse(String text, Locale locale) {
            return LocalDateTime.parse(text);
        }

        @Override
        public String print(LocalDateTime date, Locale locale) {
            return date.format(DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_PATTERN));
        }
    }

    public static class LocalDateFormatter implements Formatter<LocalDate> {

        @Override
        public LocalDate parse(String text, Locale locale) {
            return LocalDate.parse(text);
        }

        @Override
        public String print(LocalDate date, Locale locale) {
            return date.format(DateTimeFormatter.ofPattern(DatePattern.NORM_DATE_PATTERN));
        }
    }

    public static class LocalTimeFormatter implements Formatter<LocalTime> {

        @Override
        public LocalTime parse(String text, Locale locale) {
            return LocalTime.parse(text);
        }

        @Override
        public String print(LocalTime date, Locale locale) {
            return date.format(DateTimeFormatter.ofPattern(DatePattern.NORM_TIME_PATTERN));
        }
    }

}
