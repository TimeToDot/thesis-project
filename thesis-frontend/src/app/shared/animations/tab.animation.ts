import {
  animate,
  animateChild,
  group,
  query,
  style,
  transition,
  trigger,
} from '@angular/animations';

export const tabAnimation = trigger('tabs', [
  transition(':increment', [
    style({ position: 'relative' }),
    query(':enter, :leave', [
      style({
        position: 'absolute',
        top: 0,
        right: 0,
        width: '100%',
      }),
    ]),
    query(':enter', [style({ right: '-100%', opacity: 0 })]),
    query(':leave', animateChild()),
    group([
      query(':leave', [
        animate('500ms ease-out', style({ right: '100%', opacity: 0 })),
      ]),
      query(':enter', [
        animate('500ms ease-out', style({ right: '0%', opacity: 1 })),
      ]),
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
        width: '100%',
      }),
    ]),
    query(':enter', [style({ left: '-100%', opacity: 0 })]),
    query(':leave', animateChild()),
    group([
      query(':leave', [
        animate('500ms ease-out', style({ left: '100%', opacity: 0 })),
      ]),
      query(':enter', [
        animate('500ms ease-out', style({ left: '0%', opacity: 1 })),
      ]),
      query('@*', animateChild(), { optional: true }),
    ]),
  ]),
]);
