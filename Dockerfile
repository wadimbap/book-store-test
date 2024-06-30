FROM openjdk:17-jdk-slim

ENV APP_HOME /app
ENV MAVEN_HOME /usr/share/maven
ENV MAVEN_CONFIG /root/.m2

RUN apt-get update && \
    apt-get install -y maven && \
    rm -rf /var/lib/apt/lists/*

WORKDIR $APP_HOME

COPY . .

RUN mvn package -DskipTests

EXPOSE 8080

CMD ["java", "-jar", "target/book-store-0.0.1-SNAPSHOT.jar"]
