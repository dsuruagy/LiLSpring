#Spring: Spring Security
22/12/2020

## Chapter 1 - Spring Security Concepts	
### Some terminology
* PII - Personal Identifiable Information

###Authentication vs. authorization
####Authentication
* Determination of who
* Technically it is the determination if a principal is who they say they are
* Principal can be humans or machines
* Spring provides out-of-box support for:
	* HTTP basic, digest, x509, and forms-based authentication (basic)
	* LDAP and Active Directory (for enterprise)
	* OpendID, Jasig CAS (Central Authentication Service) JASS (internet)
	* Kerberos and SAML

#### Authorization
* Determines what the principal can or cannot do
* Authorization is based on authentication
* Authorization is called access control
* Spring supports:
	* Web request (used in this course)
	* Method invocation
	* Domain object instance access control

## Chapter 2 - Securing Web Applications with Spring
### Implementing basic authentication
* By adding spring-boot-starter-security dependency, Spring provides basic forms-based authentication for our application.

### In-memory authentication
**WARNING** 
* This is not for production use cases in most scenarios
* This is part of the progression of learning only

### JDBC authentication

### Leveraging bcrypt for Hashing
#### Password Rules
* Never store plaintext
* Never encrypt
* SHA-256 is considered crackable
* bcrypt is the best option today

### Authorization
1. create auth.AuthGroup entity
2. create auth.AuthGroupRepository with a method to get AuthGroups by username
3. Add a list of AuthGroup in auth.LandonUserPrincipal. Change getAuthorities() to return grantedAuthorities created with the AuthGroup name. 
4. Add the LandonUserPrincipal AuthGroup initialization on LandonUserDetailsService.loadUserByUsername().
5. Use @PreAuthorize to control access to the methods getGuests(), getGuest() to ROLE_USER, and getAddGuestForm(), addGuest(), updateGuest() to ROLE_ADMIN. 
6. ApplicationSecurityConfiguration:
	* @EnableGlobalMethodSecurtiy(prePostEnable = true), to enable method security.
	* On our database, we only have values USER and ADMIN, but in our methods we have ROLE_USER and ROLE_ADMIN. We need to map these values with a bean GrantedAuthoritiesMapper. Set into authenticationProvider().
	
### Form-based authentication
#### Basic Authentication
* Basic is in the spec; forms isn't
* Cannot support logging off
* No real security implications with either, assuming TLS is used

#### Forms-Based Authentication
* Allows you to customize the form
* Allows for a more seamless view in your application
* Provides "remember me" options
* Provides logout

#### Steps
* Implement login form
* Implement logout page
* Turn it on (change from basic authentication to forms-based authentication)

## Chapter 3 - LDAP Authentication
### LDAP for authentication
#### Why LDAP
* LDAP is lightweight, especially for user authentication - Lightweight Directory Access Protocol
* Built into many operating systems
* Interoperability
* Scalability

#### Spring Security LDAP
* spring-security-ldap project
* Full support for native LDAP operators
* Password-hashing algorithms included

#### Paradigm
* Very similar to basic and forms-based authentication
* Leverages AuthenticationManagerBuilder in the same manner

#### Internal LDAP
* Use embedded LDAP
* Can use OpenLDAP if you prefer
* AD is not LDAP, but can use LDAP for authentication

### Configuring an Embedded LDAP Server
* Add dependencies:
    * spring-security-ldap
    * unboundid-ldapsdk
    
* application.properties:
    * spring.ldap.embedded.ldif (points to the file with the LDAP structure)
    * spring.ldap.embedded.base-dn
    * spring.ldap.embedded.port
    
### Implementing LDAP for authentication
* Remove everything related to JDBC (com.frankmoley.security.app.auth package)
* Remove JPA and H2 dependencies from the pom.xml file
* Remove data.sql and schema.sql files
* Configure LDAP authentication in ApplicationSecurityConfiguration file:
    * remove the LandonUserDetailService and DAOAuthenticationProvider.
    * fill the configure with LDAP authentication related code. 