import { ProjectTask } from '../../projects/models/project-task.model';
import { Project } from '../../projects/models/project.model';
import { Status } from '../enum/status.enum';

export interface EmployeeTask {
  id: string;
  startDate: string;
  endDate: string;
  startTime: string;
  endTime: string;
  project: Project;
  task: ProjectTask;
  status: Status;
}
