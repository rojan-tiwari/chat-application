
import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  
  loginRequest = {
    username: '',
    password: ''
  };
  
  constructor(private authService: AuthService) { }
  
  ngOnInit(): void {
  }
  
  login() {
    this.authService.login(this.loginRequest).subscribe(response => {
      console.log(response);
    });
  }
}