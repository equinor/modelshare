#Modelshare

Model storage and sharing on web.

Web application to handle storage and sharing of model files used by Statoil and others with the appropriate access rights.

The [design document](https://docs.google.com/a/itema.no/document/d/1Q-6XYVCCoVEz7N6S7dGUnP9NCEu3OxQoUsgpqvg4yVY/edit?usp=sharing) describes how this will be implemented.

## The ".passwd" file

This files lists the users and their credentials. It is on the form:

    <e-mail-address>:<password hash>:<parent group idenfier>:<full name>
    <group identifier>:x:<parent group identifier>:<full name>:

If the user password field is empty, the user may log in without specifying a password. Placing an "x" in the password field indicates that this is a group and not a user that may log in. For testing purposes one may add a user by seting the ".passwd" file contents to `test:::Test User`.

The default location of the file is at `${user.home}/modelshare/repository/`.
