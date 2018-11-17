[![Build Status](https://travis-ci.org/torgammelgard/learning-assistant.svg?branch=master)](https://travis-ci.org/torgammelgard/learning-assistant)

# Learning Assistant
A database project (MongoDB) for a school assignment.

This assistant offers the user to, 
- create new cards dynamically with information like questions and multiple choice answers
- delete cards
- edit cards
- show cards
- statistical information about the user's progress
- ...

# Installation instructions
```
./gradlew fatJar war
docker-compose build
docker-compose up
java -jar ./build/libs/learning-assistant-all.jar
```

Go to [http://127.0.0.1:8080/learning-assistant/](http://127.0.0.1:8080/learning-assistant/)

## How to login to the mongo database
```
docker exec -it mongo-db bash
mongo -u root -p example --authenticationDatabase admin
```
Mongo commands,
```
show dbs
use learning_assistant
show collections
```
