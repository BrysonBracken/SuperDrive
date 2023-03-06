# SuperDrive

SuperDrive is a service application built for the Udacity Nano degree program. It is a Spring Boot app built to provide file storage, note management, and credential management.

### Installation
1. Download repository
2. Open in IDE.
3. Run CloudStorageApplication.java
4. Visit localhost:8080/login

### Tested Conditions
* Tests will pass under the following conditions:
* 
* testCredentialCreate - Creates a new note and details are visible.
* testCredentialUpdate - Updates a credential and details are visible.
* testCredentialDelete - Deletes a credential and details are not visible.
* addCreds - Creates a new credential and details are visible.
* testNoteCreate - Creates a new note and details are visible.
* testNoteUpdate - Updates a note and details are visible.
* testNoteDelete - Deletes a note and details are not visible.
* addNote - Creates a new note and details are visible.
* testSignupLogin - User is created and can log in.


### Built With

* Spring Boot - Framework providing dependency injection, web framework, data binding, resource management, transaction management, and more.
* MyBatis - Persistence framework that suppports custom SQL mappings.
