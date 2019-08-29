package org.dummy.gsddays

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.HandlerFunction
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerRequest
import reactor.core.publisher.Mono
import org.springframework.web.reactive.function.server.RequestPredicates
import org.springframework.web.reactive.function.server.RouterFunctions
import org.springframework.web.reactive.function.server.RouterFunction

@Component
class RestApplication {
    @Component
    class RouteHandler {
        fun hello(request: ServerRequest): Mono<ServerResponse> {
            return ServerResponse.ok().contentType(MediaType.TEXT_PLAIN)
                    .body(BodyInserters.fromObject("Hello, Spring!"))
        }
    }

    @Configuration
    class Router {
        @Bean
        fun route(routeHandler: RouteHandler): RouterFunction<ServerResponse> {
            return RouterFunctions
                .route(
                    RequestPredicates.GET("/hello").and(RequestPredicates.accept(MediaType.TEXT_PLAIN)),
                    HandlerFunction<ServerResponse>(routeHandler::hello)
                )
        }
    }
}
