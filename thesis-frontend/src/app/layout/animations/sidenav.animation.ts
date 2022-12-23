import {
  animate,
  animateChild,
  group,
  query,
  style,
  transition,
  trigger,
} from '@angular/animations';

export const sidenavAnimation = trigger('sidenavTabs', [
  transition(':increment', [
    style({ position: 'relative' }),
    query(':enter, :leave', [
      style({
        position: 'absolute',
        bottom: 0,
        right: 0,
        height: '100%',
        width: '100%',
      }),
    ]),
    query(':enter', [style({ bottom: '-100%' })]),
    query(':leave', animateChild()),
    group([
      query(':leave', [animate('500ms ease-out', style({ bottom: '100%' }))]),
      query(':enter', [animate('500ms ease-out', style({ bottom: '0%' }))]),
      query('@*', animateChild(), { optional: true }),
    ]),
  ]),

  transition(':decrement', [
    style({ position: 'relative' }),
    query(':enter, :leave', [
      style({
        position: 'absolute',
        top: 0,
        left: 0,
        height: '100%',
        width: '100%',
      }),
    ]),
    query(':enter', [style({ top: '-100%' })]),
    query(':leave', animateChild()),
    group([
      query(':leave', [animate('500ms ease-out', style({ top: '100%' }))]),
      query(':enter', [animate('500ms ease-out', style({ top: '0%' }))]),
      query('@*', animateChild(), { optional: true }),
    ]),
  ]),
]);
