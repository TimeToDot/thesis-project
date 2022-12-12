import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ButtonComponent } from '../button/button.component';
import {
  animate,
  state,
  style,
  transition,
  trigger,
} from '@angular/animations';
import { ToastService } from '../../services/toast.service';

@Component({
  selector: 'bvr-toast',
  standalone: true,
  imports: [ButtonComponent, CommonModule],
  templateUrl: './toast.component.html',
  animations: [
    trigger('toastTrigger', [
      state('open', style({ transform: 'translateY(0%)' })),
      state('close', style({ transform: 'translateY(200%)' })),
      transition('open <=> close', [animate('300ms ease-in-out')]),
    ]),
  ],
})
export class ToastComponent implements OnInit {
  message: string = 'Employee added';
  showToast: boolean = false;
  styleClass: string = 'success';

  constructor(public toastService: ToastService) {}

  ngOnInit(): void {
    setTimeout(() => {
      this.showToast = true;
    }, 1000);
  }

  dismiss(): void {
    this.toastService.dismissToast();
  }
}
