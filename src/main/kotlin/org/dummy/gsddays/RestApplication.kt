package org.dummy.gsddays

import java.sql.ResultSet
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.HandlerFunction
import org.springframework.web.reactive.function.server.RequestPredicates
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

@Component
class RestApplication {

    @Component
    class RouteHandler(val jdbcTemplate: JdbcTemplate) {
        fun hello(request: ServerRequest): Mono<ServerResponse> {

            val rowMapper: RowMapper<String> = RowMapper<String> { resultSet: ResultSet, _: Int ->
                resultSet.getString("name")
            }

            val name = jdbcTemplate.query("SELECT name from names LIMIT 1", rowMapper)[0]

            return ServerResponse
                .ok()
                .contentType(MediaType.TEXT_PLAIN)
                .body(BodyInserters.fromValue("Hello, $name!"))
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
