The "repository" folder contains a sample repository, it should only be used
for testing. In order to use it one can add the following line to the launch 
configuration for the server:

	-Drepository.root=${resource_loc:/com.statoil.modelshare}/repository

## Testing mail sending

A Docker container is running MailHog at "aediculus.local", it's web interface
can be reached using http://aediculus.local:32768. This project has already
been set up to use the SMTP server on this host.
