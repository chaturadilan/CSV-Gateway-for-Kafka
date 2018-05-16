FROM anapsix/alpine-java
MAINTAINER Chatura Dilan

ARG JAR_FILE
ENV JAR_FILE ${JAR_FILE}

ADD  target/${JAR_FILE} ${JAR_FILE}
ADD  target/conf/log4j.properties conf/log4j.properties
ADD  target/conf/config.properties conf/config.properties

RUN  mkdir -p /data
VOLUME /data
CMD java -jar ${JAR_FILE} /data
