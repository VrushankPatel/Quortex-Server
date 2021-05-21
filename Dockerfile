FROM maven:3.6.0-jdk-11-slim AS build
COPY src /app/src
COPY pom.xml /app
ENV quortex_mail=quortexapp@gmail.com
ENV quortex_pwd=Quortex@1998
ENV SPRING_PROFILES_ACTIVE=local 
ENV DATABASE_URL=postgres://sasqynncqlennh:4e4e5cd74f0aba7456ba5a24182876362ce39a87e3fcd3648c3136d345676700@ec2-107-22-14-60.compute-1.amazonaws.com:5432/d11hjd3h8aa4q8 
RUN mvn -f /app/pom.xml clean package

FROM openjdk:11-jre-slim
COPY --from=build /app/target/Quortex-0.0.1-SNAPSHOT.jar /usr/local/lib/quortex.jar
ENV quortex_mail=quortexapp@gmail.com                                                                                                                         
ENV quortex_pwd=Quortex@1998                                                                                                                                  
ENV SPRING_PROFILES_ACTIVE=local 
ENV DATABASE_URL=postgres://sasqynncqlennh:4e4e5cd74f0aba7456ba5a24182876362ce39a87e3fcd3648c3136d345676700@ec2-107-22-14-60.compute-1.amazonaws.com:5432/d11hjd3h8aa4q8
EXPOSE 8080
CMD ["java", "-jar", "/usr/local/lib/quortex.jar"]
