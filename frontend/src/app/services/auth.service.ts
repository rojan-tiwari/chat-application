import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { RoutingConstants } from '../routing-constants';
import { User,LoginRequest } from '../models/user.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root' 
})
export class AuthService {
  private baseUrl = environment.restServiceUrl;
  private currentUser: string | null = null;

  constructor(private http: HttpClient) {}

  setCurrentUser(username: string) {
    this.currentUser = username;
  }

  getCurrentUser(): string | null {
    return this.currentUser;
  }

  registerUser(user: User): Observable<any> {
    return this.http.post(this.baseUrl + RoutingConstants.AUTH + RoutingConstants.REGISTER, user);
  }

  login(loginRequest: LoginRequest): Observable<any> {
    return this.http.post(this.baseUrl + RoutingConstants.AUTH + RoutingConstants.LOGIN, loginRequest);
  }
}
