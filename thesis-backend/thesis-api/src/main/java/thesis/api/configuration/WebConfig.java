package thesis.api.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import thesis.api.configuration.converter.CaseInsensitiveEnumConverter;
import thesis.api.employee.model.calendar.CalendarTaskStatus;
import thesis.api.employee.model.task.TaskStatus;
import thesis.domain.project.model.approval.ApprovalStatus;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        List<Class<? extends Enum>> enums = List.of(TaskStatus.class, ApprovalStatus.class, CalendarTaskStatus.class);

        enums.forEach(enumClass -> registry.addConverter(String.class, enumClass,
                new CaseInsensitiveEnumConverter<>(enumClass)));
    }
}
