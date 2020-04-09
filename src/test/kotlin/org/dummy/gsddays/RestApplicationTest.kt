package org.dummy.gsddays

import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Tag("integration-test")
class RestApplicationTest {
    @Autowired
    private val webTestClient: WebTestClient? = null

    @Test
    fun testHello() {
        webTestClient!!
            .get().uri("/hello")
            .accept(MediaType.TEXT_PLAIN)
            .exchange()
            .expectStatus().isOk
            .expectBody<String>()
            .isEqualTo("Hello, aramirez-es!")
    }
}
