package thesis.api;


import thesis.domain.paging.PagingSettings;

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

    protected PagingSettings initPaging(Integer page, Integer size, String key, String direction){
        var settings = new PagingSettings();

        if (page != null) settings.setPage(page);
        if (size != null) settings.setSize(size);
        if (key != null) settings.setKey(key);
        if (direction != null) settings.setDirection(direction);

        return settings;
    }
}
