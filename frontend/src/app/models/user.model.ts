
export interface User {
  id?: number;
  username?: string;
  password?: string;
}

export interface LoginResponse {
  token: string;
  
}

export interface RegisterResponse {
  message: string;

}


