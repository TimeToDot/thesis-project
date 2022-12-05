import { Status } from '../../shared/enum/status.enum';

export interface Day {
  date: string;
  disabled: boolean;
  status?: Status;
}
