
import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';


@Component({
  selector: 'app-register',
  standalone: true,
  imports : [FormsModule,CommonModule],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  
   user = {
    firstName: '',
    lastName: '',
    username: '',
    email: '',
    password: '',
    confirmPassword: '',  
    acceptTerms: false
  };

  isLoading = false;
  successMessage = '';
  errorMessage = '';
  showPassword = false;

  
  constructor(private authService: AuthService) { }
  
  ngOnInit(): void {
  }

  togglePassword() {
    this.showPassword = !this.showPassword;
  }

  getPasswordStrength() {
    // logic to calculate password strength (0-100)
    return 50; // example value
  }

  getPasswordStrengthClass() {
    // example returning CSS class based on strength
    return 'medium-strength';
  }

    getPasswordStrengthText() {
    // example text based on strength
    return 'Medium';
  }
  
  registerUser() {
    this.authService.registerUser(this.user).subscribe(response => {
      console.log(response);
    });
  }
}