FROM maven:3.8.1-openjdk-17-slim AS builder
COPY CAcert.cer /tmp
COPY Cert.cer /tmp
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package -DskipTests
FROM openjdk:17-jdk-slim
COPY --from=builder /tmp/CAcert.cer /tmp
COPY --from=builder /tmp/Cert.cer /tmp
RUN keytool -cacerts -storepass changeit -noprompt -trustcacerts -importcert -alias root-ca-giust -file /tmp/CAcert.cer
RUN keytool -cacerts -storepass changeit -noprompt -trustcacerts -importcert -alias intermidiate-ca-giust -file /tmp/Cert.cer
COPY --from=builder /home/app/target/backoffice-0.0.1-SNAPSHOT.jar /usr/local/lib/backoffice.jar
#EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/backoffice.jar"]
