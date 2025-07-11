
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { User } from '../models/user.model';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  
  private apiUrl = 'http://localhost:8080/api/auth';
  
  constructor(private http: HttpClient) { }
  
  registerUser(user: User) {
    return this.http.post(`${this.apiUrl}/register`, user);
  }
  
  login(loginRequest: any) {
    return this.http.post(`${this.apiUrl}/login`, loginRequest);
  }
}
