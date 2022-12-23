import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ButtonComponent } from '../../../shared/components/button/button.component';
import { ActivatedRoute, Router, RouterLinkWithHref } from '@angular/router';
import { FormFieldComponent } from '../../../shared/components/form-field/form-field.component';
import { ModalComponent } from '../../../shared/components/modal/modal.component';
import { Position } from '../../models/position.model';
import { ToastComponent } from '../../../shared/components/toast/toast.component';
import { ToastService } from '../../../shared/services/toast.service';
import { ToastState } from '../../../shared/enum/toast-state';
import { PositionsService } from '../../services/positions.service';
import { first } from 'rxjs';

@Component({
  selector: 'bvr-view-position',
  standalone: true,
  imports: [
    ButtonComponent,
    CommonModule,
    FormFieldComponent,
    ModalComponent,
    RouterLinkWithHref,
    ToastComponent,
  ],
  templateUrl: './view-position.component.html',
})
export class ViewPositionComponent implements OnInit {
  isArchiveModalOpen: boolean = false;
  modalDescription: string = '';
  position: Position = {
    id: '',
    name: '',
    description: '',
    creationDate: '',
    count: 0,
    archiveDate: '',
    active: true,
  };

  constructor(
    private positionsService: PositionsService,
    private route: ActivatedRoute,
    private router: Router,
    private toastService: ToastService
  ) {}

  ngOnInit(): void {
    this.getPosition();
  }

  getPosition(): void {
    const positionId = this.route.snapshot.paramMap.get('id');
    if (positionId) {
      this.positionsService
        .getPosition(positionId)
        .pipe(first())
        .subscribe(position => {
          this.position = position;
        });
    }
  }

  openArchiveModal(): void {
    this.isArchiveModalOpen = true;
    this.modalDescription = `Are you sure you want to archive position ${this.position.name}? This action cannot be undone.`;
  }

  archive(): void {
    this.router.navigate(['..'], { relativeTo: this.route }).then(() => {
      setTimeout(
        () =>
          this.toastService.showToast(ToastState.Success, 'Position archived'),
        200
      );
      setTimeout(() => this.toastService.dismissToast(), 3200);
    });
  }
}
