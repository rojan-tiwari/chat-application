import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { RoutingConstants } from '../routing-constants';
import { User } from '../models/user.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root' 
})
export class AuthService {
  private baseUrl = environment.restServiceUrl;

  constructor(private http: HttpClient) {}

  registerUser(user: User): Observable<any> {
    return this.http.post(this.baseUrl + RoutingConstants.AUTH + RoutingConstants.REGISTER, user);
  }

  login(loginRequest: any): Observable<any> {
    return this.http.post(this.baseUrl + RoutingConstants.AUTH + RoutingConstants.LOGIN, loginRequest);
  }
}
