# Builder image of multi stage
FROM maven:3-jdk-11-slim AS build

# Create working directory and create files
WORKDIR /app
COPY . .

# Run Maven and copy target file
RUN mvn clean package
RUN mv com.equinor.modelshare/target/modelshare*.jar modelshare.jar

# Runtime of multi stage
FROM adoptopenjdk/openjdk11:alpine-slim

################################################################################
LABEL com.equinor.modelshare.maintainer="Torkild U. Resheim <tur@itema.no>" \
      com.equinor.modelshare.description="Equinor model sharing service"
################################################################################

# Set working directory and copy artifact
WORKDIR /app
COPY --from=build /app/modelshare.jar /app/modelshare.jar

# Ports and entry point
EXPOSE 80  
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom", "-Drepository.root=/repository", "-jar","/app/modelshare.jar","--server.port=80"] 
