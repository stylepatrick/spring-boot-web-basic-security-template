# spring-boot-web-basic-security-template
 Basic Auth and Oauth2 login possibilities.
 Basic HTTP Auth with WebMVC and Spring Security. User Repository to PostgresDB and different Roles.
 Different Hashs can be used by setting a {} prefix infront of the password. Example {bcrypt} or {sha256}.
 Oauth2 is connection to the Google Client (API-Gateway) to make the authentication. 
 
 Endpoints:
  - /login for general login
  - /logout for logout
  - /api is public without auth
  - /api/secret auth needed
  - /api/topsecret auth and role TOPSECRET needed
  - /api/user returns the user principal