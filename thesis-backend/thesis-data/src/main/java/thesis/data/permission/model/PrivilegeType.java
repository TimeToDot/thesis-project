package thesis.data.permission.model;

public enum PrivilegeType {
  //project
  //modification
  CAN_READ_PROJECT,
  CAN_MANAGE_TASKS,
  CAN_MANAGE_PROJECT_USERS,
  //administration
  CAN_READ_ADMIN,
  CAN_ADMIN_PROJECTS,

  //global
  CAN_READ,
  CAN_ADMIN_USERS,

  CAN_CREATE_USERS,

  CAN_ADMIN_SETTINGS,

  CAN_ADMIN_POSITIONS
}
