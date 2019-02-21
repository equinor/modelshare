# Modelshare

_Modelshare_ is a software service that supports storage and sharing of 
simulation model files.

The [design document](https://docs.google.com/a/itema.no/document/d/1Q-6XYVCCoVEz7N6S7dGUnP9NCEu3OxQoUsgpqvg4yVY/edit?usp=sharing) 
describes the design up to the first release (0.8.0). Development has 
recommenced and new features will be added.

## Building and running

Building and running the project requires that you have **Java 11** on your 
path. In addition you must have a recent version of Apache Maven.

There are no profiles or special settings that must be enabled, so the project
can be easily built by executing `mvn clean verify` from the root folder.

Once the build has completed you can start an instance on port 8080 from the 
command line by executing:

	java -jar -Drepository.root=./com.equinor.modelshare/repository/ \
		./com.equinor.modelshare/target/modelshare.jar
	
Note that the _repository.root_ parameter points to a repository that has been
created for test and demo purposes. It has a _.passwd_ file with a list of users
that have access. You can edit the file and it will reload automatically while
the application is running. Try logging in as _super@equinor.com_.

Note that when testing e-mail features, the e-mail address should be a working 
one, unless you have set up i.e. MailHog.

### Mail configuration

Several features in the service requires a mail server to be present. While
developing and testing the best option is probably to start an instance of 
[Mailhog](https://github.com/mailhog/MailHog).

    docker pull mailhog/mailhog && docker run -d -p 1025:1025 -p 8025:8025 mailhog/mailhog

The service is preconfigured to use port 1025 on localhost for SMTP, which are
the defaults for MailHog. Open a browser at http://localhost:8025 to see what's
going on with the e-mails. In production the following properties must be set in
*modelshare.properties*:

	smtp.host=localhost
	smtp.port=1025

### Running within Eclipse

A preconfigured launch configuration has been made. Open the **Debug** or 
**Run** menu and select **Launch ModelShare...**. This will start the 
application at port 8080. 

## The repository

This service uses a very simple approach to storing it's data. Everything 
related to the model database are kept as raw files and stored under one 
location.

The [pages](https://github.com/equinor/modelshare/tree/master/com.equinor.modelshare/repository/pages)
folder contains MarkDown formatted files that are automatically converted to 
HTML. The file _index.md_ is used to render the front page of the application.
There is also the _terms.md_ file which is expected to be present.

### The data model

![The Modelshare data model](https://github.com/equinor/modelshare/raw/master/modelshare.png "Modelshare data model")

For each folder in the repository there is a _*.modeldata_ file that is 
basically a serialization of a _Folder_ entity. Likewise there is a 
_*.modeldata_ file for each _Model_ stored in the folders. Some models are very
simple and contain little information.  Others can be parsed (such as SIMA 
models) and the metadata file will contain some more information.

The account related information is created from various sources at runtime and
is only kept in memory.     

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
