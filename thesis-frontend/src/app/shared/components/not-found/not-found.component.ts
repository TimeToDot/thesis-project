import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { RouterLinkWithHref } from '@angular/router';
import { ButtonComponent } from '../button/button.component';

@Component({
  selector: 'bvr-not-found',
  templateUrl: './not-found.component.html',
  standalone: true,
  imports: [ButtonComponent, CommonModule, RouterLinkWithHref],
})
export class NotFoundComponent implements OnInit {
  constructor() {}

  ngOnInit(): void {}
}
