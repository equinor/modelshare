# Builder image of Multistage
FROM maven:3-jdk-11-slim AS build

# Create Workdir and create files
WORKDIR /app
COPY . .

# Run Maven and Copy target file
RUN mvn clean package
RUN mv com.equinor.modelshare/target/modelshare*.jar app.jar

# Runtime of Multistage
FROM adoptopenjdk/openjdk11:alpine-slim

################################################################################
LABEL com.equinor.modelshare.maintainer="Torkild U. Resheim <tur@itema.no>" \
      com.equinor.modelshare.description="Equinor model sharing service"
################################################################################

# Set Workdir and Copy Artifact
WORKDIR /app
COPY --from=build /app/app.jar /app/app.jar

# Ports and Entrypoint
EXPOSE 8080  
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app/app.jar"] 
