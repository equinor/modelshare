# Modelshare

Model storage and sharing on web.

Web application to handle storage and sharing of model files used by Statoil and others with the appropriate access rights.

The [design document](https://docs.google.com/a/itema.no/document/d/1Q-6XYVCCoVEz7N6S7dGUnP9NCEu3OxQoUsgpqvg4yVY/edit?usp=sharing) describes how this will be implemented.

## The repository

This service uses a very simple approach to stor it's data. Everything is file
based and stored under one location.

The [pages](https://github.com/equinor/modelshare/tree/master/com.statoil.modelshare/repository/pages)
folder contains MarkDown formatted files that are automatically converted to 
HTML. The file _index.md_ is used to render the front page of the application. 

The "repository" folder in the _com.statoil.modelshare_ project contains a 
sample repository, it should only be used for testing. In order to use it one 
can add the following line to the launch configuration for the server:

	-Drepository.root=${resource_loc:/com.statoil.modelshare}/repository

## Mail configuration

Several features in the service requires a mail server to be present. While
developing the best option is probably to start an instance of 
[Mailhog](https://github.com/mailhog/MailHog).

    docker pull mailhog/mailhog && docker run -d -p 1025:1025 -p 8025:8025 mailhog/mailhog

The service is preconfigured to use port 1025 on localhost for SMTP, which are
the defaults for MailHog. Open a browser at http://localhost:8025 to see what's
going on with the e-mails. In production the following properties must be set in
*modelshare.properties*:

	smtp.host=localhost
	smtp.port=1025

## Users and groups

Users can be managed through the **Administration > Manage users** interface. 
However that requires that the logged in user belongs to the built in 
"supervisor" group. This can be set by initially adding this user to the 
.passwd-file.

### The ".passwd" file

This files lists the users and their credentials. It is on the form:

    <e-mail-address>:<password hash>:<parent group idenfier>:<full name>:<organization>:<local user>:<password reset token>
    <group identifier>:x:<parent group identifier>:<full name>:

If the user password field is empty, the user may log in without specifying a 
password. Placing an "x" in the password field indicates that this is a group 
and not a user that may log in. For testing purposes one may add a user by 
setting the ".passwd" file contents to `test:::Test User`. The following example
file show a user "user" that does not need a password and a user "admin" with 
the a hashed password.

	administrators:x::Administrators
	users:x::Users
	admin:$2a$10$gYjUwUZ7nGa3BmD4qdffCejlxON0oXXpqB00t7SxGulrvju9FGX1W:administrators:Admin User
	user::users:User

The default location of the file is at the repository root.

### The "supervisor" group

This group is automatically created by the system and will have full access to 
everything. In order to place a user in the supervisor group it is sufficient to
declare the user as member of that group. It is also possible to create a group
that is member of the supervisor group.

## Running within Eclipse

A preconfigured launch configuration has been made. Open the **Debug** or 
**Run** menu and select **Launch ModelShare...**. This will start the 
application at port 8080. 
