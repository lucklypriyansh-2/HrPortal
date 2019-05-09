Hr portal
   Steps to run
     1. Download  git project 
     2. import in eclipse as maven
     3  run as maven clean install
     4  run as java application




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


Algorithms for problem 
Possible Solution Approaches 

Approach 1
											    Time    Space 	
1 GET LIST OF RELATION FROM UI AND SAVE IN DB  (O(E))    (O(1))
2 CREATE TREE USING HASHMAP IN MEMORY          (O(E))    (O(E))
3 DETECT LOOP IN TREE USING DFS(ITERATIVE)     (O(E))    (O(E))

DISADVANTAGE 
1 HIGH SPACE COMPLEXITY DUE TO IN MEMORY KEEPING UP OF EDGE AND BUILDING TREE
2 PROGRAMME READABILITY WILL BE BAD SINCE WILL USE ITERATIVE APPORACH

ADVANTAGES
1 FAST SINCE ALL COMPUTATION IS HAPPENING IN MEMEORY

APPROACH 2    CHOSEN APPORACH

1 GET LIST OF RELATION FROM UI AND SAVE IN DB     (O(E)) (O(1))
2 USE RECURSIVE APPORACH TO GET LIST OF EDGES   (O(E)) (O(H)) AND DETECT LOOP AT SAME TIME BY CALLING DB 
3 RETURN RESPONSE

ADVANTAGE 
1 SPACE WILL BE RELATIVELY LOW SINCE WE ARE NOT KEEPING  ALL  EDGE IN MEMORY INSTED CALLING DB 
2 WE ARE DETECTING LOOP AND BUILDING TREE AT SAME TIME THIS CAUSE ONLY ONE O(N) OPERATION AND REDUCE COMPUTATION TIME

DISADVANTAGES
1 RECURSION IS SLOW APPROACH TO BUILD THE TREE AND CAN CAUSE STACK OVERFLOW IN CASE DEPTH OF TREE GOES BEYOND A VERY HIGH NUMBER

APPROACH 3    

THE DISDVANTAGE OF ABOVE APPORACH CAN BE REMOVED BY USING ITERATIVE APPORACH OF BUILDING TREE HYBRID OF  APPORACH 1+APPROACH 2

IT WILL HAVE BEST OF BOTH THE APPROACH (SPEED + SPACE)
DISADVANTAGES
1 PROGRAM READABILITY WILL REDUCE A LOT IF CHOSEN APPORACH 3

