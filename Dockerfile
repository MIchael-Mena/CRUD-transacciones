FROM adoptopenjdk:11-jre-hotspot
MAINTAINER Michael
COPY out/artifacts/CRUD_transacciones_jar/*.jar /CRUD_transacciones.jar
ENTRYPOINT ["java","-jar","/CRUD_transacciones.jar"]