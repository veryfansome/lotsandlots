
clean::
	mvn clean

run::
	mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Dconfig.file=$$HOME/lotsandlots.conf"

test:: clean
	mvn test clean