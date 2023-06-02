#FROM adoptopenjdk:11-jdk
#COPY out/artifacts/CRUD_transacciones_jar/*.jar /CRUD_transacciones.jar
#ENTRYPOINT ["java","-jar","/CRUD_transacciones.jar"]

# Usa una imagen base que incluya Tomcat
FROM tomcat:9.0.64-jdk11

MAINTAINER Michael

# Exponer el puerto 8080 para acceder a la aplicación
EXPOSE 8080

# Copia el archivo war a la carpeta webapps de Tomcat
COPY CRUD_transacciones-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/CRUD_transacciones.war

# Comando para iniciar Tomcat al ejecutar el contenedor
CMD ["catalina.sh", "run"]

