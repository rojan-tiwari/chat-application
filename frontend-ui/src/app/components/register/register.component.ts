// src/app/components/register/register.component.ts

import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  
  user = {
    username: '',
    password: ''
  };
  
  constructor(private authService: AuthService) { }
  
  ngOnInit(): void {
  }
  
  registerUser() {
    this.authService.registerUser(this.user).subscribe(response => {
      console.log(response);
    });
  }
}