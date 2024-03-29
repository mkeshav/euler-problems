FROM hseeberger/scala-sbt:17.0.2_1.6.2_3.1.1
LABEL Author="Keshav Murthy"

RUN \
  apt-get update && \
  apt-get install -y \
    gcc libz-dev \
    curl \
    jq \
    wget \
    zip unzip

ENV SONAR_SCANNER_VERSION 5.0.1.3006
RUN wget -q https://binaries.sonarsource.com/Distribution/sonar-scanner-cli/sonar-scanner-cli-${SONAR_SCANNER_VERSION}-linux.zip -P /tmp/
RUN unzip /tmp/sonar-scanner-cli-${SONAR_SCANNER_VERSION}-linux.zip -d /usr/local/
RUN chmod +x /usr/local/sonar-scanner-${SONAR_SCANNER_VERSION}-linux/bin/sonar-scanner
RUN chmod +x /usr/local/sonar-scanner-${SONAR_SCANNER_VERSION}-linux/jre/bin/java
RUN ln -s /usr/local/sonar-scanner-${SONAR_SCANNER_VERSION}-linux/bin/sonar-scanner /usr/bin/sonar-scanner
WORKDIR /app

#Create docker container with the dependencies so subsequent builds are faster
ADD build.sbt ./
ADD ./project/plugins.sbt ./project/
RUN sbt update

# Build app
ADD . /app
