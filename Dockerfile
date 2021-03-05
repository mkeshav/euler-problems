FROM adoptopenjdk/openjdk11 as dev

LABEL Author="Keshav Murthy"

ENV SBT_VERSION 1.4.7

RUN apt-get update && \
    apt-get -y install curl

RUN \
  curl -L -o sbt-$SBT_VERSION.deb http://dl.bintray.com/sbt/debian/sbt-$SBT_VERSION.deb && \
  dpkg -i sbt-$SBT_VERSION.deb && \
  rm sbt-$SBT_VERSION.deb && \
  apt-get update && \
  apt-get install -y \
    sbt \
    jq \
    wget \
    zip unzip

WORKDIR /app

#Create docker container with the dependencies so subsequent builds are faster
ADD build.sbt ./
ADD ./project/plugins.sbt ./project/
RUN sbt update

ENV SONAR_SCANNER_VERSION 3.3.0.1492
RUN wget -q https://binaries.sonarsource.com/Distribution/sonar-scanner-cli/sonar-scanner-cli-${SONAR_SCANNER_VERSION}-linux.zip -P /tmp/
RUN unzip /tmp/sonar-scanner-cli-${SONAR_SCANNER_VERSION}-linux.zip -d /usr/local/
RUN chmod +x /usr/local/sonar-scanner-${SONAR_SCANNER_VERSION}-linux/bin/sonar-scanner
RUN chmod +x /usr/local/sonar-scanner-${SONAR_SCANNER_VERSION}-linux/jre/bin/java
RUN ln -s /usr/local/sonar-scanner-${SONAR_SCANNER_VERSION}-linux/bin/sonar-scanner /usr/bin/sonar-scanner

# Build app
ADD . /app