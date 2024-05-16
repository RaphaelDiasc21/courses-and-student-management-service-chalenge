run:
	mvn clean package -DskipTests=true
	docker-compose down
	docker-compose up --build