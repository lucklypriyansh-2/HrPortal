docker rmi $(docker images -qa -f 'dangling=true')
docker stop $(docker ps -q --filter ancestor=hrportal )
./mvnw clean package docker:build
docker run -p 8080:8080 hrportal