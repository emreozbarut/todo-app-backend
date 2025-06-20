# todo-app-backend
- You can reach swagger-ui with [SWAGGER-UI](http://localhost:8084/swagger-ui/index.html)
- You can reach couchbase with [COUCHBASE-UI](http://localhost:8091)
- You can pull docker image from [DOCKER-HUB](https://hub.docker.com/r/yasinozbarut/todo-app-backend)

# Build your application
## Maven build
```bash
mvn clean package
```

## Start the services
```bash
docker-compose up --build -d
```

# Run the test suite
## Maven test
```bash
mvn test
```

# VM Options
### You can run application locally with above VM options
```
-Dspring.profiles.active=your-active-profile (dev-test-prod)
-DJWT_SECRET=your-jwt-secret
-DADMIN_PASSWORD=your-admin-password
-DSPRING_COUCHBASE_CONNECTION_STRING=your-couchbase-connection-string
-DSPRING_COUCHBASE_USERNAME=your-couchbase-username
-DSPRING_COUCHBASE_PASSWORD=your-couchbase-password
-DSPRING_COUCHBASE_BUCKET_NAME=your-couchbase-bucket-name
```