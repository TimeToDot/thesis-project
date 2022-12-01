import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ButtonComponent } from '../../shared/components/button/button.component';
import { CdkTableModule } from '@angular/cdk/table';
import { FormsModule } from '@angular/forms';
import { Position } from '../model/position.model';
import { PositionsService } from '../services/positions.service';

@Component({
  selector: 'bvr-admin-positions',
  standalone: true,
  imports: [ButtonComponent, CdkTableModule, CommonModule, FormsModule],
  templateUrl: './admin-positions.component.html',
})
export class AdminPositionsComponent implements OnInit {
  dataSource: Position[] = [];
  displayedColumns: string[] = [
    'name',
    'description',
    'employeesCount',
    'actions',
  ];
  query: string = '';

  constructor(private positionsService: PositionsService) {}

  ngOnInit(): void {
    this.dataSource = this.positionsService.getPositions();
  }
}
