Hr portal
  
Hosted URL

https://hrportal-app.herokuapp.com/

Swagger 
https://hrportal-app.herokuapp.com/swagger-ui.html

Build Job 

https://dashboard.heroku.com/apps/hrportal-app/access
Please raise request to get  build job access


FOR RUNNING LOCALLY

PreRequisite
    Docker should be installed

Steps to run for mac/Linux
    1 Download the project
    2 cd /Project_location
    3 sh buildrunscript.sh

Steps to run for Windows
mvnw.cmd clean package docker:build
docker run -p 8080:8080 hrportal



API


URL 

CREATE HERIARCHY REQUEST

Authentication is done using headers Cookie:sessionId=<XYZ>

POST Hierarchy  to post data

curl -X POST \
  http://localhost:8080/Hierarchy \
  -H 'Content-Type: application/json' \
  -H 'Cookie: sessionId=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjcmVkIiwidXNlcm5hbWUiOiJjbGFyaW9uIn0.cnPobo3qHtn5ijKYuZNrPKdnp5FAunf2MHn8KmB49PE' \
  -H 'cache-control: no-cache' \
  -d '{
"Pete": "Nick",
"Barbara": "Nick",
"Nick": "Sophie",
"Sophie": "Jane"


}'

GET Heriarchy to get supervisor

curl -X GET \
  http://localhost:8080/Hierarchy/Barbara \
  -H 'Cookie: sessionId=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjcmVkIiwidXNlcm5hbWUiOiJjbGFyaW9uIn0.cnPobo3qHtn5ijKYuZNrPKdnp5FAunf2MHn8KmB49PE' \
  -H 'cache-control: no-cache'


POST for getting token  Login request

curl -X POST \
  http://localhost:8080/login \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: d4516851-3e8e-4a2f-afa8-8bba91e84902' \
  -H 'cache-control: no-cache' \
  -d '{
	"userName":"clarion",
	"password":"<will send from mail>"
}'

