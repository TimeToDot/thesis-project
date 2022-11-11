import { TestBed } from '@angular/core/testing';

import { AuthService } from './auth.service';

describe('AuthService', () => {
  let service: AuthService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AuthService);

    let store: { [key: string]: any } = {};
    const mockLocalStorage = {
      getItem: (key: string): string => {
        return key in store ? store[key] : null;
      },
      setItem: (key: string, value: string) => {
        store[key] = `${value}`;
      },
    };

    spyOn(localStorage, 'getItem').and.callFake(mockLocalStorage.getItem);
    spyOn(localStorage, 'setItem').and.callFake(mockLocalStorage.setItem);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should not set isLoggedIn', () => {
    service.readFromLocalStorage();
    expect(service.isLoggedIn).toBeFalse();
  });

  it('should set isLoggedIn', () => {
    localStorage.setItem('user', 'true');
    service.readFromLocalStorage();
    expect(service.isLoggedIn).toBeTrue();
  });

  it('should log in', () => {
    service.login().subscribe(() => {
      expect(service.isLoggedIn).toBeTrue();
      expect(localStorage.getItem('user')).toBe('true');
    });
  });

  it('should log out', () => {
    service.logout();
    expect(service.isLoggedIn).toBeFalse();
    expect(localStorage.getItem('user')).toBe('false');
  });
});
