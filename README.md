# spring-boot-example

![Master](https://github.com/dummy-org-gsd-days/spring-boot-example/workflows/Master/badge.svg)

Exposes a simple `/hello` endpoint. You can run the application:

```bash
$ ./gradlew bootRun
```

And then call it:

```bash
$ curl -i http://localhost:8080/hello
HTTP/1.1 200 OK
Content-Type: text/plain
Content-Length: 14

Hello, Spring!
```

## Development

### Running tests

```bash
$ ./gradlew clean check
```
