export interface ProjectTask {
  id: string;
  name: string;
  description: string;
  creationDate: string;
  archiveDate?: string;
  projectId: string;
  active: boolean;
}
