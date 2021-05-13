FROM hseeberger/scala-sbt:graalvm-ce-21.1.0-java11_1.5.2_2.13.5
LABEL Author="Keshav Murthy"

RUN \
  apt-get update && \
  apt-get install -y \
    curl \
    jq \
    wget \
    zip unzip

WORKDIR /app

#Create docker container with the dependencies so subsequent builds are faster
ADD build.sbt ./
ADD ./project/plugins.sbt ./project/
RUN sbt update

ENV SONAR_SCANNER_VERSION 4.6.0.2311
RUN wget -q https://binaries.sonarsource.com/Distribution/sonar-scanner-cli/sonar-scanner-cli-${SONAR_SCANNER_VERSION}-linux.zip -P /tmp/
RUN unzip /tmp/sonar-scanner-cli-${SONAR_SCANNER_VERSION}-linux.zip -d /usr/local/
RUN chmod +x /usr/local/sonar-scanner-${SONAR_SCANNER_VERSION}-linux/bin/sonar-scanner
RUN chmod +x /usr/local/sonar-scanner-${SONAR_SCANNER_VERSION}-linux/jre/bin/java
RUN ln -s /usr/local/sonar-scanner-${SONAR_SCANNER_VERSION}-linux/bin/sonar-scanner /usr/bin/sonar-scanner

RUN gu install native-image

# Build app
ADD . /app