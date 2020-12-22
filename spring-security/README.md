#Spring: Spring Security
22/12/2020

	
### Some terminology
* PII - Personal Identifiable Information

##Authentication vs. authorization
###Authentication
* Determination of who
* Technically it is the determination if a principal is who they say they are
* Principal can be humans or machines
* Spring provides out-of-box support for:
	* HTTP basic, digest, x509, and forms-based authentication (basic)
	* LDAP and Active Directory (for enterprise)
	* OpendID, Jasig CAS (Central Authentication Service) JASS (internet)
	* Kerberos and SAML

### Authorization
* Determines what the principal can or cannot do
* Authorization is based on authentication
* Authorization is called access control
* Spring supports:
	* Web request (used in this course)
	* Method invocation
	* Domain object instance access control

### Implementing basic authentication
* By adding spring-boot-starter-security dependency, Spring provides basic forms-based authentication for our application.

### In-memory authentication
**WARNING** 
* This is not for production use cases in most scenarios
* This is part of the progression of learning only
