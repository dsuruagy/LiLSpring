# Spring: Test-Driven Development with JUnit

29/10/2020

## MySQL installation

[How To Install MySQL on Ubuntu 20.04](https://www.digitalocean.com/community/tutorials/how-to-install-mysql-on-ubuntu-20-04)
[Install MySQL on Ubuntu 20.04 LTS Linux](https://linuxconfig.org/install-mysql-on-ubuntu-20-04-lts-linux)


Execute the following commands:
    
    sudo systemctl start mysql

    sudo mysql
    mysql> CREATE USER 'tddshonna'@'localhost' IDENTIFIED BY 'boothH2!';
    mysql> create database `spring-tdd`;
    mysql> grant create, alter, drop, insert, update, delete, select on `spring-tdd`.* to 'tddshonna'@'localhost';
    
MySQL Workbench:
- install from snap: mysql-workbench-community.

    
    sudo apt install gnome-keyring
    sudo snap connect mysql-workbench-community:password-manager-service :password-manager-service
    
## 2. Testing Spring Service Components
### 2.2 Write integration tests for @Service

#### Setup Preview

Instruct JUnit to do the following
* **Not** load @controllers
* **Only** load @service and dependencies
* Examples: @datarepository and similar
* Connect to a **real** data source - test-specific or other staging.


- Execute the *ContactsManagementServiceIntegrationTest* class tests.
- See the results on MySQL workbench:


    SELECT id, first_name, last_name FROM `spring-tdd`.customer_contact;
    
### 2.3 Write unit tests for @Service

#### Setup Preview

Instruct JUnit to do the following
* **Not** load @controllers
* Load **mocks** for @service and its dependencies
* Example: @data*
* Use Mockito for our mocking framework
* There is **no** data source to configure

## 3.2 Write integration tests for @Controller
#### Setup Preview

Instruct JUnit to do the following
* Provide full servlet engine behavior
* Load @controllers
* Load @service
* Load @datarepository
