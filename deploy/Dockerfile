FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
ADD hello-service-0.1.0.jar app.jar
ADD wait-for-it.sh wait-for-it.sh
RUN sh -c 'touch /app.jar'
RUN chmod +x 'wait-for-it.sh'
ENV JAVA_OPTS=""
