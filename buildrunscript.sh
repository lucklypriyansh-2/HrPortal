./mvnw clean package docker:build
docker run -p 8080:8080 hrportal