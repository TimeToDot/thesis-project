import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { ProjectPermissions } from '../model/project-permissions.model';
import { PermissionsService } from '../../shared/services/permissions.service';

import { ProjectComponent } from './project.component';

describe('ProjectComponent', () => {
  let component: ProjectComponent;
  let fixture: ComponentFixture<ProjectComponent>;
  let permissionsService: PermissionsService;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProjectComponent, RouterTestingModule],
    }).compileComponents();

    fixture = TestBed.createComponent(ProjectComponent);
    permissionsService = TestBed.inject(PermissionsService);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should not get navbar options', () => {
    const permissionsServiceSpy = spyOn(
      permissionsService,
      'getProjectPermissions'
    );

    component.getNavbarOptions();

    expect(permissionsServiceSpy).toHaveBeenCalledOnceWith(null);
  });

  it('should get navbar options', () => {
    const mockPermissionsService = {
      getProjectPermissions: (index: string | null): ProjectPermissions => {
        return {
          id: '1',
          canReadProject: true,
          canManageTasks: true,
          canManageProjectEmployees: true,
          canManageApprovals: true,
          canAdminProjects: true,
          canCreateProjectEmployee: true,
        };
      },
    };

    const permissionsServiceSpy = spyOn(
      permissionsService,
      'getProjectPermissions'
    ).and.callFake(mockPermissionsService.getProjectPermissions);

    component.getNavbarOptions();

    expect(permissionsServiceSpy).toHaveBeenCalledOnceWith(null);
  });
});
