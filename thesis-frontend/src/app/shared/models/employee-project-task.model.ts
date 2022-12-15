import { Project } from '../../projects/models/project.model';
import { EmployeeTask } from './employee-task.model';

export interface EmployeeProjectTask {
  project: Project;
  tasks: EmployeeTask[];
}
