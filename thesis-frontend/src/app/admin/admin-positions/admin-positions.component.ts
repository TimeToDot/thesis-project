import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ButtonComponent } from '../../shared/components/button/button.component';
import { CdkTableModule } from '@angular/cdk/table';
import { FormsModule } from '@angular/forms';
import { Position } from '../model/position.model';

@Component({
  selector: 'bvr-admin-positions',
  standalone: true,
  imports: [ButtonComponent, CdkTableModule, CommonModule, FormsModule],
  templateUrl: './admin-positions.component.html',
})
export class AdminPositionsComponent {
  dataSource: Position[] = [
    {
      name: 'Frontend Developer',
      description: 'Frontend Developer',
    },
    {
      name: 'Product Designer',
      description: 'Product Designer',
    },
  ];
  displayedColumns: string[] = ['name', 'description', 'actions'];
  query: string = '';
}
