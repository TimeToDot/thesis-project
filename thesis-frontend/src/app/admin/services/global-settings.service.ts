import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { GlobalSettings } from '../models/global-settings.model';

@Injectable({
  providedIn: 'root',
})
export class GlobalSettingsService {
  constructor(private http: HttpClient) {}

  getGlobalSettings(): Observable<GlobalSettings> {
    return this.http.get<GlobalSettings>(
      `${environment.apiUrl}/global-settings`
    );
  }

  updateGlobalSettings(
    globalSettings: GlobalSettings
  ): Observable<GlobalSettings> {
    return this.http.put<GlobalSettings>(
      `${environment.apiUrl}/global-settings`,
      globalSettings
    );
  }
}
