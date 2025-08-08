
import { Component, OnInit, Optional } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';


@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule,CommonModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})

export class LoginComponent implements OnInit {

  
  loginRequest = {
    username: '',
    password: ''
  };

  errorMessage: string = ''; 
  isLoading = false;  
  showPassword = false;

  
  constructor(private authService: AuthService, private router: Router) {
}

  
  ngOnInit(): void {
  }

  togglePassword() {
    this.showPassword = !this.showPassword;
  }

  
   login() {
    this.isLoading = true; 
    this.authService.login(this.loginRequest).subscribe({
      next: (response) => {
        const username = response.username || this.loginRequest.username;
        this.authService.setCurrentUser(username);
        this.isLoading = false; 
        this.router.navigate(['/chat']); 
      },
      error: (error) => {
        console.error(error);
        this.isLoading = false; 
      }
    });
  }
}