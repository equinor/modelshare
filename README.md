#Modelshare

Model storage and sharing on web.

Web application to handle storage and sharing of model files used by Statoil and others with the appropriate access rights.

The [design document](https://docs.google.com/a/itema.no/document/d/1Q-6XYVCCoVEz7N6S7dGUnP9NCEu3OxQoUsgpqvg4yVY/edit?usp=sharing) describes how this will be implemented.

## The ".passwd" file

This files lists the users and their credentials. It is on the form:

    <e-mail-address>:<password hash>:<parent group idenfier>:<full name>
    <group identifier>:x:<parent group identifier>:<full name>:

If the user password field is empty, the user may log in without specifying a password. Placing an "x" in the password field indicates that this is a group and not a user that may log in. For testing purposes one may add a user by setting the ".passwd" file contents to `test:::Test User`. The following example file show a user "test" that does not need a password and a user "admin" with the password "admin".

	administrators:x::Administrators
	users:x::Users
	admin:$2a$10$gYjUwUZ7nGa3BmD4qdffCejlxON0oXXpqB00t7SxGulrvju9FGX1W:administrators:Admin User
	user::users:User

The default location of the file is at `${user.home}/modelshare/repository/`.

Modelshare is installed on the following server: 84.16.202.251
The server is physically placed in the DORA building, but is eventually intended to be moved to Statoil. 
To log in to the server via remote desktop use the following:
user: itema
password: 1tema1sKing

Tomcat 8 is installed as web-container.
To administrate Tomcat go to the following site: 84.16.202.251/manager
Clik the Manage button and log in using the follwing:
user: admin
password: Kokke Limonke 123


The following document was agreed upon as the first delivery:
https://docs.google.com/document/d/1Q-6XYVCCoVEz7N6S7dGUnP9NCEu3OxQoUsgpqvg4yVY/edit#heading=h.s1zs8p6fault

Bengt has sent a document to Statoil where we have proposed further work.
