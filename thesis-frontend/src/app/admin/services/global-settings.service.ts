import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { GlobalSettings } from '../models/global-settings.model';

@Injectable({
  providedIn: 'root',
})
export class GlobalSettingsService {
  private url: string = 'http://localhost:3000/global-settings';

  constructor(private http: HttpClient) {}

  getGlobalSettings(): Observable<GlobalSettings> {
    return this.http.get<GlobalSettings>(this.url);
  }
}
