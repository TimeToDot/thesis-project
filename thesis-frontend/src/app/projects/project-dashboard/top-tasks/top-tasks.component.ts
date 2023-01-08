import { Component, HostListener } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Chart } from 'chart.js/auto';

@Component({
  selector: 'bvr-top-tasks',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './top-tasks.component.html',
})
export class TopTasksComponent {
  chart: any;
  contentOffset: number = 1140;
  contentSmallOffset: number = 864;

  constructor() {}

  ngOnInit(): void {
    this.createChart();
  }

  ngAfterViewInit(): void {
    window.dispatchEvent(new Event('resize'));
  }

  createChart(): void {
    this.chart = new Chart('TopTasks', {
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
