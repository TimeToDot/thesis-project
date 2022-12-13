import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ButtonComponent } from '../../shared/components/button/button.component';
import { CdkTableModule } from '@angular/cdk/table';
import { FormsModule } from '@angular/forms';
import { Position } from '../models/position.model';
import { PositionsService } from '../services/positions.service';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'bvr-admin-positions',
  standalone: true,
  imports: [
    ButtonComponent,
    CdkTableModule,
    CommonModule,
    FormsModule,
    RouterModule,
  ],
  templateUrl: './admin-positions.component.html',
})
export class AdminPositionsComponent implements OnInit {
  dataSource: Position[] = [];
  displayedColumns: string[] = ['name', 'description', 'count', 'actions'];
  query: string = '';
  showActive: boolean = true;

  constructor(private positionsService: PositionsService) {}

  ngOnInit(): void {
    this.dataSource = this.positionsService.getPositions();
  }
}
