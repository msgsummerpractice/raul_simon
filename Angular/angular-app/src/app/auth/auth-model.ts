export type AuthResponse = {
  accessToken: string;
  username: string;
  roles: string[];
};

export type User = {
  username: string;
  roles: string[];
};

export type MfaRequest = {
  username: string;
  mfaCode: string;
};

export type LoginRequest = {
  username: string;
  password: string;
};

export type JwtPayload = {
  sub: string;
  roles: string[];
  exp: number;
};
