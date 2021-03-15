## Home assignment project for SKF AI

#### How to run application
1.You need to have docker and docker-compose installed on your system
https://docs.docker.com/engine/install/
https://docs.docker.com/compose/install/

2.Execute following command in the project root folder:
```
docker-compose up --build
```

3.Application is accessible on http://localhost:18080

#### Available endpoints:
* _POST http://localhost:18080/messages/publish_
```
{
    "content": "New message to publish"
}
```
* _GET http://localhost:18080/messages/getLast_
* _GET http://localhost:18080/messages/getByTime_

Optional URL params:
```
start - digit timestamp (milliseconds) to filter messages created time [from]
end - digit timestamp (milliseconds) to filter messages created time [to]
```

