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
  transition(
    `dashboard => tracker,
    dashboard => projects,
    dashboard => employees,
    dashboard => positions,
    dashboard => settings,
    tracker => projects,
    tracker => employees,
    tracker => positions,
    tracker => settings,
    projects => employees,
    projects => positions,
    projects => settings,
    employees => positions,
    employees => settings,
    positions => settings`,
    [
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
    ]
  ),

  transition(
    `settings => positions,
    settings => employees,
    settings => projects,
    settings => tracker,
    settings => dashboard,
    positions => employees,
    positions => projects,
    positions => tracker,
    positions => dashboard,
    employees => projects,
    employees => tracker,
    employees => dashboard,
    projects => tracker,
    projects => dashboard,
    tracker => dashboard`,
    [
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
    ]
  ),
]);
