#Modelshare

Model storage and sharing on web.

Web application to handle storage and sharing of model files used by Statoil and others with the appropriate access rights.

The [design document](https://docs.google.com/a/itema.no/document/d/1Q-6XYVCCoVEz7N6S7dGUnP9NCEu3OxQoUsgpqvg4yVY/edit?usp=sharing) describes how this will be implemented.

## Users and groups

Users can be managed through the **Administration > Manage users** interface. However that requires that the logged in user belongs to the built in "supervisor" group. This can be set by initially adding this user to the .passwd-file.

### The ".passwd" file

This files lists the users and their credentials. It is on the form:

    <e-mail-address>:<password hash>:<parent group idenfier>:<full name>:<organization>:<local user>:<password reset token>
    <group identifier>:x:<parent group identifier>:<full name>:

If the user password field is empty, the user may log in without specifying a password. Placing an "x" in the password field indicates that this is a group and not a user that may log in. For testing purposes one may add a user by setting the ".passwd" file contents to `test:::Test User`. The following example file show a user "test" that does not need a password and a user "admin" with the password "admin".

	administrators:x::Administrators
	users:x::Users
	admin:$2a$10$gYjUwUZ7nGa3BmD4qdffCejlxON0oXXpqB00t7SxGulrvju9FGX1W:administrators:Admin User
	user::users:User

The default location of the file is at `${user.home}/modelshare/repository/`.

### The "supervisor" group

This group is automatically created by the system and will have full access to everything. In order to place a user in the supervisor group it is sufficient to declare the user as member of that group. It is also possible to create a group that is member of the supervisor group.

## Server installation

Modelshare is installed on http://84.16.202.251:8080/modelshare/. The server is physically placed in the DORA building, but is eventually intended to be moved to Statoil. To log in to the server via remote desktop use the following:

* user: itema
* password: 1tema1sKing

Tomcat 8 is installed as servlet-container. To administrate Tomcat go to the following site: http://84.16.202.251:8080/manager/. Click the Manage button and log in using the following:

* user: admin
* password: Kokke Limonke 123
