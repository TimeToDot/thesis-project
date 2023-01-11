/*privileges*/
/*project*/
INSERT INTO privilege(name) VALUES ('CAN_READ_PROJECT');
INSERT INTO privilege(name) VALUES ('CAN_MANAGE_TASKS');
INSERT INTO privilege(name) VALUES ('CAN_MANAGE_PROJECT_USERS');
INSERT INTO privilege(name) VALUES ('CAN_READ_ADMIN');
INSERT INTO privilege(name) VALUES ('CAN_ADMIN_PROJECTS');
/*global*/
INSERT INTO privilege(name) VALUES ('CAN_READ');
INSERT INTO privilege(name) VALUES ('CAN_ADMIN_USERS');
INSERT INTO privilege(name) VALUES ('CAN_CREATE_USERS');
INSERT INTO privilege(name) VALUES ('CAN_ADMIN_SETTINGS');
INSERT INTO privilege(name) VALUES ('CAN_ADMIN_POSITIONS');

/*roles*/
INSERT INTO role(name) VALUES ('ROLE_GLOBAL_ADMIN');
INSERT INTO role(name) VALUES ('ROLE_GLOBAL_USER');

INSERT INTO role(name) VALUES ('ROLE_PROJECT_ADMIN');
INSERT INTO role(name) VALUES ('ROLE_PROJECT_MODERATOR');
INSERT INTO role(name) VALUES ('ROLE_PROJECT_USER');
/*role_privilege*/
/*global*/
/*user*/
INSERT INTO role_privilege(role_id, privilege_id) select role.id, privilege.id from role, privilege where role.name = 'ROLE_GLOBAL_USER' and privilege.name = 'CAN_READ';
/*admin*/
INSERT INTO role_privilege(role_id, privilege_id) select role.id, privilege.id from role, privilege where role.name = 'ROLE_GLOBAL_ADMIN' and privilege.name = 'CAN_READ';
INSERT INTO role_privilege(role_id, privilege_id) select role.id, privilege.id from role, privilege where role.name = 'ROLE_GLOBAL_ADMIN' and privilege.name = 'CAN_ADMIN_USERS';
INSERT INTO role_privilege(role_id, privilege_id) select role.id, privilege.id from role, privilege where role.name = 'ROLE_GLOBAL_ADMIN' and privilege.name = 'CAN_CREATE_USERS';
INSERT INTO role_privilege(role_id, privilege_id) select role.id, privilege.id from role, privilege where role.name = 'ROLE_GLOBAL_ADMIN' and privilege.name = 'CAN_ADMIN_SETTINGS';
INSERT INTO role_privilege(role_id, privilege_id) select role.id, privilege.id from role, privilege where role.name = 'ROLE_GLOBAL_ADMIN' and privilege.name = 'CAN_ADMIN_POSITIONS';
/*project*/
/*user*/
INSERT INTO role_privilege(role_id, privilege_id) select role.id, privilege.id from role, privilege where role.name = 'ROLE_PROJECT_USER' and privilege.name = 'CAN_READ_PROJECT';
/*moderator*/
INSERT INTO role_privilege(role_id, privilege_id) select role.id, privilege.id from role, privilege where role.name = 'ROLE_PROJECT_MODERATOR' and privilege.name = 'CAN_READ_PROJECT';
INSERT INTO role_privilege(role_id, privilege_id) select role.id, privilege.id from role, privilege where role.name = 'ROLE_PROJECT_MODERATOR' and privilege.name = 'CAN_MANAGE_TASKS';
INSERT INTO role_privilege(role_id, privilege_id) select role.id, privilege.id from role, privilege where role.name = 'ROLE_PROJECT_MODERATOR' and privilege.name = 'CAN_MANAGE_PROJECT_USERS';
/*admin*/
INSERT INTO role_privilege(role_id, privilege_id) select role.id, privilege.id from role, privilege where role.name = 'ROLE_PROJECT_ADMIN' and privilege.name = 'CAN_READ_PROJECT';
INSERT INTO role_privilege(role_id, privilege_id) select role.id, privilege.id from role, privilege where role.name = 'ROLE_PROJECT_ADMIN' and privilege.name = 'CAN_MANAGE_TASKS';
INSERT INTO role_privilege(role_id, privilege_id) select role.id, privilege.id from role, privilege where role.name = 'ROLE_PROJECT_ADMIN' and privilege.name = 'CAN_MANAGE_PROJECT_USERS';
INSERT INTO role_privilege(role_id, privilege_id) select role.id, privilege.id from role, privilege where role.name = 'ROLE_PROJECT_ADMIN' and privilege.name = 'CAN_READ_ADMIN';
INSERT INTO role_privilege(role_id, privilege_id) select role.id, privilege.id from role, privilege where role.name = 'ROLE_PROJECT_ADMIN' and privilege.name = 'CAN_ADMIN_PROJECTS';

/*accounts*/

/*account_details*/

/*account_role*/


/*position*/
INSERT INTO position(name, description, status) VALUES ('EMPLOYEE_I', 'fresh worker', 'ACTIVE');
INSERT INTO position(name, description, status) VALUES ('EMPLOYEE_II', 'fresh worker', 'ACTIVE');
INSERT INTO position(name, description, status) VALUES ('MANAGER_I', 'fresh worker', 'ACTIVE');
INSERT INTO position(name, description, status) VALUES ('MANAGER_II', 'fresh worker', 'ACTIVE');