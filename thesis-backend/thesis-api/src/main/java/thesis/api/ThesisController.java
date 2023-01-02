package thesis.api;


public abstract class ThesisController {
    //project
    //modification
    protected final String CAN_READ_PROJECT = "CAN_READ_PROJECT";
    protected final String CAN_MANAGE_TASKS = "CAN_MANAGE_TASKS";
    protected final String CAN_MANAGE_PROJECT_USERS = "CAN_MANAGE_PROJECT_USERS";
    //administration
    protected final String CAN_READ_ADMIN = "CAN_READ_ADMIN";
    protected final String CAN_ADMIN_PROJECTS = "CAN_ADMIN_PROJECTS";

    //global
    protected final String CAN_READ = "CAN_READ";
    protected final String CAN_ADMIN_USERS = "CAN_ADMIN_USERS";
    protected final String CAN_CREATE_USERS = "CAN_CREATE_USERS";
    protected final String CAN_ADMIN_SETTINGS = "CAN_ADMIN_SETTINGS";
    protected final String CAN_ADMIN_POSITIONS = "CAN_ADMIN_POSITIONS";
}
