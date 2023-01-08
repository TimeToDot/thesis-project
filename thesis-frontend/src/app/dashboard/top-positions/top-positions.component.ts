import { Component, HostListener, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Chart } from 'chart.js/auto';

@Component({
  selector: 'bvr-top-positions',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './top-positions.component.html',
})
export class TopPositionsComponent implements OnInit {
  chart: any;
  contentOffset: number = 1140;
  contentSmallOffset: number = 864;

  constructor() {}

  ngOnInit(): void {
    this.createChart();
  }

  createChart(): void {
    this.chart = new Chart('TopPositions', {
      type: 'pie',
      data: {
        labels: [
          'Dispatcher',
          'Forklift Operator',
          'Warehouseman',
          'Accountant',
          'Forwarder',
        ],
        datasets: [
          {
            data: ['32', '16', '8', '4', '2'],
            backgroundColor: [
              '#73696c',
              '#887e80',
              '#b2a7aa',
              '#d0c4c8',
              '#faeef2',
            ],
          },
        ],
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
        plugins: {
          legend: {
            position: 'bottom',
          },
        },
      },
    });
  }

  @HostListener('window:resize', ['$event'])
  onResize(event: any): void {
    const width =
      event.target.innerWidth > 1200
        ? event.target.innerWidth - this.contentOffset
        : event.target.innerWidth - this.contentSmallOffset;
    this.chart.canvas.parentNode.style.width = `${width}px`;
  }
}
