FROM hseeberger/scala-sbt:11.0.14.1_1.6.2_3.1.1
LABEL Author="Keshav Murthy"

RUN \
  apt-get update && \
  apt-get install -y \
    gcc libz-dev \
    curl \
    jq \
    wget \
    zip unzip

WORKDIR /app

#Create docker container with the dependencies so subsequent builds are faster
ADD build.sbt ./
ADD ./project/plugins.sbt ./project/
RUN sbt update

# Build app
ADD . /app
