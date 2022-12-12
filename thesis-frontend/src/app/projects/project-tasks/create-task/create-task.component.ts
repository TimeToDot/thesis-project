import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ButtonComponent } from '../../../shared/components/button/button.component';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { FormFieldComponent } from '../../../shared/components/form-field/form-field.component';
import { ModalComponent } from '../../../shared/components/modal/modal.component';

@Component({
  selector: 'bvr-create-task',
  standalone: true,
  imports: [
    ButtonComponent,
    CommonModule,
    FormFieldComponent,
    ModalComponent,
    ReactiveFormsModule,
    RouterModule,
  ],
  templateUrl: './create-task.component.html',
})
export class CreateTaskComponent {
  createProjectTaskForm!: FormGroup;
  isModalOpen: boolean = false;
  modalDescription: string = '';

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.createForm();
  }

  createForm(): void {
    this.createProjectTaskForm = this.fb.group({
      name: ['', [Validators.required]],
      description: ['', [Validators.required]],
    });
  }

  openModal(): void {
    this.isModalOpen = true;
    const taskName = this.createProjectTaskForm.get(['name'])?.value;
    this.modalDescription = `Do you want to add ${taskName} to the Project X?`;
  }

  confirm(): void {
    this.router.navigate(['..'], { relativeTo: this.route });
  }

  isRequired(name: string): boolean {
    return this.createProjectTaskForm
      .get(name)
      ?.hasValidator(Validators.required)
      ? true
      : false;
  }
}
