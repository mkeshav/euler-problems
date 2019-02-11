FROM openjdk:11.0.2-slim as dev
LABEL Author="Keshav Murthy"

ENV SBT_VERSION 1.2.8

RUN apt-get update && \
    apt-get -y install curl

RUN \
  curl -L -o sbt-$SBT_VERSION.deb http://dl.bintray.com/sbt/debian/sbt-$SBT_VERSION.deb && \
  dpkg -i sbt-$SBT_VERSION.deb && \
  rm sbt-$SBT_VERSION.deb && \
  apt-get update && \
  apt-get install -y \
    sbt \
    python-pip jq \
    zip unzip

WORKDIR /app

#Create docker container with the dependencies so subsequent builds are faster
ADD build.sbt ./
ADD ./project/plugins.sbt ./project/
RUN sbt update

# --- Build image
FROM dev as builder

# Build app
ADD . /app