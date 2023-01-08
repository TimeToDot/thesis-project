import { Component, HostListener, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Chart } from 'chart.js/auto';

@Component({
  selector: 'bvr-hours-in-projects',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './hours-in-projects.component.html',
})
export class HoursInProjectsComponent implements OnInit {
  chart: any;
  contentOffset: number = 776;
  contentSmallOffset: number = 582;

  constructor() {}

  ngOnInit(): void {
    this.createChart();
  }

  createChart(): void {
    this.chart = new Chart('HoursInProjects', {
      type: 'bar',
      data: {
        labels: [
          'January',
          'February',
          'March',
          'April',
          'May',
          'June',
          'July',
        ],
        datasets: [
          {
            label: "McDonald's",
            data: ['542', '542', '536', '327', '17', '0.00', '538'],
            backgroundColor: '#f00a20',
          },
          {
            label: 'Amazon',
            data: ['542', '542', '536', '327', '17', '0.00', '538'],
            backgroundColor: '#f8a72f',
          },
          {
            label: 'Fedex',
            data: ['542', '542', '536', '327', '17', '0.00', '538'],
            backgroundColor: '#544a4c',
          },
          {
            label: 'Starbucks',
            data: ['467', '576', '572', '79', '92', '574', '573'],
            backgroundColor: '#0d6238',
          },
        ],
      },
      options: {
        // aspectRatio: 2.5,
        responsive: true,
        maintainAspectRatio: false,
        scales: {
          x: {
            stacked: true,
          },
          y: {
            stacked: true,
          },
        },
        plugins: {
          legend: {
            display: false,
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
