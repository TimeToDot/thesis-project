package thesis.api;


import org.mapstruct.ap.shaded.freemarker.template.SimpleDate;
import thesis.domain.paging.PagingSettings;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.temporal.ChronoField;
import java.util.Date;

public abstract class ThesisController {
    //project
    //modification
    protected final String CAN_READ_PROJECT = "CAN_READ_PROJECT";
    protected final String CAN_MANAGE_TASKS = "CAN_MANAGE_TASKS";
    protected final String CAN_MANAGE_PROJECT_USERS = "CAN_MANAGE_PROJECT_USERS";
    //administration
    protected final String CAN_READ_ADMIN = "CAN_ADMIN_READ";
    protected final String CAN_ADMIN_PROJECT = "CAN_ADMIN_PROJECT";

    //global
    protected final String CAN_READ = "CAN_READ";
    protected final String CAN_ADMIN_USERS = "CAN_ADMIN_USERS";
    protected final String CAN_CREATE_USERS = "CAN_CREATE_USERS";
    protected final String CAN_ADMIN_SETTINGS = "CAN_ADMIN_SETTINGS";
    protected final String CAN_ADMIN_POSITIONS = "CAN_ADMIN_POSITIONS";
    protected final String CAN_ADMIN_PROJECTS = "CAN_ADMIN_PROJECTS";

    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


    protected PagingSettings initPaging(Integer page, Integer size, String key, String direction){
        var settings = new PagingSettings();

        if (page != null) settings.setPage(page);
        if (size != null) settings.setSize(size);
        if (key != null) settings.setKey(key);
        if (direction != null) settings.setDirection(direction);

        return settings;
    }

    protected Date checkDate(Date date, Destiny destiny){
        var lDate = LocalDate.now().atStartOfDay(ZoneId.systemDefault());
        if (date == null) {
            switch (destiny) {
                case APPROVE_START -> {return Date.from(lDate.minusMonths(2).toInstant());
                }
                case APPROVE_END, TASKS_END -> {
                    return Date.from(lDate.with(ChronoField.NANO_OF_DAY, LocalTime.MAX.toNanoOfDay()).toInstant());
                }
            }
        }
        return date;
    }

    protected Date getEndOfDate(Date date){
        var lDate = LocalDate.parse(sdf.format(date));
        var endOfDay = LocalDateTime.of(lDate, LocalTime.MAX);
        return Date.from(endOfDay.toInstant(ZoneOffset.UTC));
    }

    public enum Destiny {
        TASKS_START, TASKS_END, APPROVE_START, APPROVE_END
    }
}
