export interface AuthResponse {
  accessToken: string;
  username: string;
  roles: string[];
}

export interface User {
  username: string;
  roles: string[];
}

export interface MfaRequest {
  username: string;
  mfaCode: string;
}

export interface LoginRequest {
  username: string;
  password: string;
}

export interface JwtPayload {
  sub: string;
  roles: string[];
  exp: number;
}
