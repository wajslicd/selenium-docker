FROM openjdk:8u212-jre-alpine3.9

RUN apk add curl jq

# Image Workspace
WORKDIR /usr/share/udemy

# ADD .jar from host under target directory
# into this image
ADD target/selenium-docker.jar selenium-docker.jar
ADD target/selenium-docker-tests.jar selenium-docker-tests.jar
ADD target/libs libs

# In case of any other dependencies like .csv / .json / .xls
# please ADD that as well

# ADD suite files
ADD book-flight-module.xml book-flight-module.xml
ADD search-module.xml search-module.xml

# Add healthcheck script
RUN wget https://s3.amazonaws.com/selenium-docker/healthcheck/healthcheck.sh

# BROWSER
# HUB_HOST
# MODULE
ENTRYPOINT sh healthcheck.sh
