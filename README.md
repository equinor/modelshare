# Modelshare [![Build Status](https://travis-ci.com/equinor/modelshare.svg?token=z54pKaonrDrNopwngA3z&branch=master)](https://travis-ci.com/equinor/modelshare)

_Modelshare_ is a software service that supports storage and sharing of 
simulation model files.

The [design document](https://docs.google.com/a/itema.no/document/d/1Q-6XYVCCoVEz7N6S7dGUnP9NCEu3OxQoUsgpqvg4yVY/edit?usp=sharing) 
describes the design up to the first release (0.8.0). Development has 
recommenced and new features will be added.

The below figure illustrates how a new version of Modelshare may
take part in continous integration of simulation models.

![Component Diagram](https://github.com/equinor/modelshare/blob/master/images/components.png?raw=true "Component Diagram")

## Building and running

### Building without Java

If Java is not installed on your system, the Modelshare service can run using 
Docker only. See the [README](application/README.md) in the **docker** folder.
You obviously have to have Docker installed.

### Bulding with Java

Building and running the project requires that you have **Java 11** on your 
path. In addition you must have a recent version of Apache Maven.

There are no profiles or special settings that must be enabled, so the project
can be easily built by executing `mvn clean verify` from the **application**
folder.

Once the build has completed you can start an instance on port 8080 from the 
command line by executing:

	java -jar ./com.equinor.modelshare/target/modelshare.jar

### Testing mail sending

Several features in the service requires a mail server to be available. While
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

### The repository data model

![The Modelshare data model](https://github.com/equinor/modelshare/blob/master/images/modelshare.png?raw=true "Modelshare data model")

For each folder in the repository there is a _*.modeldata_ file that is 
basically a serialization of a _Folder_ entity. Likewise there is a 
_*.modeldata_ file for each _Model_ stored in the folders. Some models are very
simple and contain little information.  Others can be parsed (such as SIMA 
models) and the metadata file will contain some more information.

The account related information is created from various sources at runtime and
is only kept in memory.     

## Authentication

There are two means of authentication in this application. One is using Active
Directory via OAuth2 and the other is using the local user system. The latter
is the default, as the Active Directory method requires configuration with
secret parameter values and thus badly serves for demonstration purposes. 
However, an instance has been set up and should be up and running. One should
be able to log in using one of the two accounts:

* admin@modelsharedirectory.onmicrosoft.com
* user@modelsharedirectory.onmicrosoft.com
* All passwords are "ModelShare2019"

If Azure AD authentication does not work, you may switch to the default 
authentication by clearing the list of active profiles:

	spring.profiles.active=
	
The configuration file can be found in `com.equinor.modelshare/src/main/resources/application.properties`.
	
### Active directory authentication

For this mechanism to work, the application must be registered in the Azure AD
instance. After creating the _application_, also make sure to set 
`"groupMembershipClaims": "All",` in the application's manifest. 

In addition the **Azure** profile must be enabled for the application. It can
be done by modifying `application.properties`:

	# In order to enable OAuth2 authentication via Azure AD, one must enable the
	# "Azure" profile. 
	spring.profiles.active=Azure
	
You may also enable the profile from the command line when starting the service.
For example:

	java -jar -Dspring.profiles.active=Azure modelshare.jar

In addition certain properties must be set:

	azure.activedirectory.tenant-id=
	azure.activedirectory.active-directory-groups=
	spring.security.oauth2.client.registration.azure.client-id=
	spring.security.oauth2.client.registration.azure.client-secret=
	modelshare.azure.activedirectory.groups=

* **azure.activedirectory.tenant-id** – Contains your Active Directory's Directory ID
* **azure.activedirectory.active-directory-groups** – Contains a list of Active Directory groups to use for authorization.
* **spring.security.oauth2.client.registration.azure.client-id** – Contains the Application ID from your application registration.
* **spring.security.oauth2.client.registration.azure.client-secret** – Contains the Value from your application registration key.
* **modelshare.azure.activedirectory.groups** – Defines all user groups in the application and maps these to Azure AD Groups. The format is &lt;group id&gt;:&lt;group name&gt;:&lt;azure group id&gt;. Multiple groups are separated by a comma. 

For more information; see [Spring Security Azure AD: Wire up enterprise grade authentication and authorization](https://azure.microsoft.com/nb-no/blog/spring-security-azure-ad/ "Spring Security Azure AD")
  

### Local user authentication

Local Users can be managed through the **Administration > Manage users** 
interface. However that requires that the logged in user belongs to the built
in "supervisor" group. This can be set by initially adding this user to the 
`.passwd`-file.

#### The ".passwd" file

This files lists the users and their credentials. It is on the form:

    <e-mail-address>:<password hash>:<parent group idenfier>:<full name>:<organization>:<local user>:<password reset token>
    <group identifier>:x:<parent group identifier>:<full name>:

If the user password field is empty, the user may log in without specifying a 
password. Placing an "x" in the password field indicates that this is a group 
and not a user that may log in. For testing purposes one may add a user by 
setting the `.passwd`-file contents to `test:::Test User`. The following example
file show a user "user" that does not need a password and a user "admin" with 
the a hashed password.

	owners:x::owners
	users:x::Users
	admin:$2a$10$gYjUwUZ7nGa3BmD4qdffCejlxON0oXXpqB00t7SxGulrvju9FGX1W:owners:Model Owner User
	user::users:User

The default location of the file is at the repository root.

### The "supervisor" group

This group is automatically created by the system and will have full access to 
everything. In order to place a user in the supervisor group it is sufficient
to declare the user as member of that group. Note that **only** members of this
group will be able to manage user accounts.

### Authorization

Once a user is authenticated one also have to be authorized in order to access
an asset. To keep track of authorizations for each of these assets a text file 
name `.access` exists in the file system. This contains a list of all users and
groups that have access (or not) to an asset. For example:

	.
	└── Njord
	    ├── Njord-P1
	    │   ├── x.stask
	    │   ├── .x.stask.access
	    │   ├── .x.stask.modeldata
	    │   ├── y.stask
	    │   ├── .y.stask.access
	    │   └── .y.stask.modeldata
	    ├── .Njord-P1.access
	    ├── Njord-P2
	    └── .Njord-P2.access

In this `.access` file one will find a list of all users and groups that have
some relation to the file and which level of access this account has. This
is given on the form `+` means access is given, `-` means access is denied and
finally `?` means access is inherited. For example `+v ?r -w` means the user (or
group is granted view access, inherits read access (from the level above) and
looses write access. If nothing is specified for the user or group it will be
inherited from the level above.
 
	lars.larsen@equinor.com             +v -r -w 
	knut.knutsen@equinor.com            -v -r -w
	tor.torsen@equinor.com              +v +r +r
	users                               +v +r +w
	guests                              +v -r -w
