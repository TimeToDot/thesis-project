import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class CountriesService {
  private url = 'https://restcountries.com/v3.1';

  constructor(private http: HttpClient) {}

  getAllCountries(): Observable<any[]> {
    return this.http.get<any>(`${this.url}/all`).pipe(
      map((countries: any[]) =>
        countries
          .map(country => {
            return { name: country.name.common, flag: country.flag };
          })
          .sort((a, b) =>
            a.name.toUpperCase() < b.name.toUpperCase() ? -1 : 1
          )
          .map((country, index) => {
            return {
              name: `${country.flag} ${country.name}`,
              id: index.toString(),
            };
          })
      )
    );
  }
}
