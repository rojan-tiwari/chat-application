
export interface User {
  id?: number;
  firstName?: string;
  lastName?: string;
  username?: string;
  password?: string;
  email?: string;
}

export interface LoginRequest {
  username?: string;
  password?: string;
  
}

export interface LoginResponse {
  username?: string;
  password?: string;
  
}

export interface RegisterResponse {
  message: string;

}


