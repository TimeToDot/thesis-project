package pl.thesis.api;

import pl.thesis.domain.paging.PagingSettings;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.temporal.ChronoField;
import java.util.Date;

public abstract class ThesisController {

    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    protected PagingSettings initPaging(){
        return new PagingSettings();
    }

    protected PagingSettings initPaging(Integer page, Integer size, String key, String direction){
        var settings = new PagingSettings();

        if (page != null) settings.setPage(1);
        if (size != null) settings.setSize(Integer.MAX_VALUE);
        if (key != null) settings.setKey(key);
        if (direction != null) settings.setDirection(direction);

        return settings;
    }

    protected Date checkDate(Date date, Destiny destiny){
        if (date == null || destiny.compareTo(Destiny.TASKS_END) == 0 || destiny.compareTo(Destiny.APPROVE_END) == 0) {
            var zDate = LocalDate.now().atStartOfDay(ZoneId.systemDefault());
            switch (destiny) {
                case APPROVE_START -> {
                    return Date.from(zDate.minusMonths(12).toInstant());
                }
                case TASKS_START -> {
                    return Date.from(zDate.toInstant());
                }
                case APPROVE_END -> {
                    return Date.from(zDate.with(ChronoField.NANO_OF_DAY, LocalTime.MAX.toNanoOfDay()).toInstant());
                }
                case TASKS_END -> {
                    var lDate = LocalDate.parse(sdf.format(date));
                    var endOfDay = LocalDateTime.of(lDate, LocalTime.MAX);
                    var zdt = ZonedDateTime.of(endOfDay, ZoneId.systemDefault());
                    return Date.from(zdt.toInstant());
                }
            }
        }
        return date;
    }

    public enum Destiny {
        TASKS_START, TASKS_END, APPROVE_START, APPROVE_END
    }
}
