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
    
### AD vs. LDAP for authentication    
#### AD is Not LDAP
* Active Directory implements an LDAP API
* Directory Services exposed via AD Lightweight Directory Services exposed as an LDAP interfaces
* AD provides many, many more services and looking at the port requirements you can get a real feel for the weight

#### Authentication with AD
* Uses standard and non-standard methods
* Configuration should be very familiar, however
* Nested groups -> SEC-1823

#### Authentication Provider
* ActiveDirectoryLdapAuthenticationProvider
* Utilized via configure method of WebSecurityConfigurerAdapter
* Mostly the same as standard LDAP
* Group to role matching work

## Chapter 4 - Leveraging OAuth 2 with Spring Security
### OAuth 2
#### What is OAuth2
* Protocol and framework for providing access to HTTP services
* Often used for third-party access
* Can be used for system-to-system communications in standalone or on behalf of a user

#### Parts of OAuth 2
* Resource owner - often the user
* Client - application requesting access
* Resource server - hosts protected data and accounts
* Authorization server - service that grants tokens

#### Token Types
* Access token - the secret and often short-lived token that identifies a user
* Refresh token - longer-lived token used to renew access token when it expires
* Scopes provide for rights associated with the access token

#### Grants
* Several grant types that impact flows
* Authorization code grant is most common
* Implicit is common in web apps and mobile apps
* Client credentials grant is useful in system-to-system comms

### Spring and OAuth 2
#### CommonOAuth2Provider 
* Provides native support for Okta, Google, GitHub and Facebook
* Property-based configuration in Spring Boot
* Client-side OAuth integration

#### Authorization Server
* Provides authorization services to the system
* @EnableAuthorizationServer
* AuthorizationServerConfigurerAdapter used to configure it
* Supports various grant types

#### Resource Server
* Provides the resources being protected
* @EnableResourceServer

#### OAuth2 Client
* Full client-side support
* @EnableOauth2Client
* Oauth2RestTemplate provides much of the scaffolding
* Supports various grant types

### Creating an OAuth authorization service
* Guest-services application
    * Add spring-security-oauth2 (version 2.3.0.RELEASE) dependency
    * Create a new package: com.frankmoley.security.services.auth
    * Create a new class: AuthorizationServerConfig:
        @EnableAuthorizationServer
        @Configuration
        extends AuthorizationServerConfigurerAdapter
        
        override configure methods
        
### Creating an OAuth resource service
* Guest-services application
    * GuestServicesApplication 
        Add @EnableResourceServer
        
* Encode username:password in base64: http://www.tuxgraphics.org/toolbox/base64-javascript.html:
    guest_app:secret - Z3Vlc3RfYXBwOnNlY3JldA==
    api_audit:secret - YXBpX2F1ZGl0OnNlY3JldA==
  
* http --form POST localhost:8100/oauth/token Authorization:"Basic Z3Vlc3RfYXBwOnNlY3JldA==" grant_type=client_credentials

* http --form localhost:8100/oauth/check_token Authorization:"Bearer 04681532-53b0-422e-bc24-8a43b9f0e279" token=04681532-53b0-422e-bc24-8a43b9f0e279

### Client-side implementations of OAuth2
* If we start our application and try to authenticate with LDAP, the client will show an error with status code of 500 (401 unauthorized is the cause).

* Guest-app application
    * Add spring-security-oauth2 (version 2.3.0.RELEASE) dependency
    * Change the com.frankmoley.security.app.service.GuestService class:
        * Add a constructor to receive a restTemplate.
    * Change the com.frankmoley.security.app.GuestAppApplication:
        * Add @EnableOauth2Client

## Chapter 5 - WebFlux security
### Introduction to WebFlux security
#### @EnableWebFluxSecurity
* Basic config maps everything to security
* SecurityWebFilterChain provides more fine-grained control
* MapReactiveUserDetailsService provides handle to UserDetailsServices

#### Principal
* Security model still based on principal
* Inject the Mono<Principal> into methods where you want a handle to it
* Still provides core functionality

