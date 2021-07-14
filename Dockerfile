FROM java:8
WORKDIR /
ADD miniProj-0.0.1-SNAPSHOT.jar miniProj-0.0.1-SNAPSHOT.jar
EXPOSE 8090
ENTRYPOINT ["java","-jar","miniProj-0.0.1-SNAPSHOT.jar"]