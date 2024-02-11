install:
	@mvn clean install -DskipTests -U -fae

test:
	@mvn test

clean:
	@mvn clean
