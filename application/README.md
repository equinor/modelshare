# Build and run docker image 

This method of running the Modelshare service locally does not require Maven or Java to be installed on your local system.

## With docker-compose

Run Modelshare with docker-compose:

    $ docker-compose run --rm --service-ports modelshare

Alternatively:

    $ docker-compose up -d
    $ docker-compose down

## With docker

Run Modelshare with docker:

    $ docker build -f docker/Dockerfile .
    $ docker run --rm -p "8080:8080" modelshare

### Accessing the service

Open [localhost:8080](http://localhost:8080) in your browser to use the service.

### Cleaning up

The builder image will leave a <none> named image on your system. To clean this out use one of the following commands:

    $ docker rmi <IMAGE_ID_OF_NONE_IMAGE>
    $ docker system prune
