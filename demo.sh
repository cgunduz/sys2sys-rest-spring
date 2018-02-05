mvn spring-boot:run -f registry/pom.xml &
sleep 2
mvn spring-boot:run -f serviceA/pom.xml &
mvn spring-boot:run -f serviceB/pom.xml &
mvn spring-boot:run -f serviceA/pom.xml -Dserver.servlet-path=/omg2 -Dserver.port=8083 &
mvn spring-boot:run -f serviceA/pom.xml -Dserver.servlet-path=/ -Dserver.port=8084 &
