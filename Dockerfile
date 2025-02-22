FROM maven:3.9.6-amazoncorretto-17 as build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:21-jdk
COPY --from=build /target/installment-0.0.1-SNAPSHOT.jar installment-0.0.1-SNAPSHOT.jar
EXPOSE 8080
CMD ["java","-jar","installment-0.0.1-SNAPSHOT.jar"]