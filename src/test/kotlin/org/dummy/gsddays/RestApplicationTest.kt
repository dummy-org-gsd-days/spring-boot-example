package org.dummy.gsddays

import java.io.File
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody
import org.testcontainers.containers.DockerComposeContainer
import org.testcontainers.containers.wait.strategy.Wait.forLogMessage
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Tag("integration-test")
class RestApplicationTest {
    @Autowired
    private val webTestClient: WebTestClient? = null

    @Container
    private var dockerEnv: DockerComposeContainer<*> = DockerEnv()
        .withServices("postgres")
        .waitingFor("postgres", forLogMessage(".*database system is ready to accept connections.*\\n", 1))
        .withLocalCompose(true)

    init {
        dockerEnv.start()
    }

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

class DockerEnv : DockerComposeContainer<DockerEnv>(File("docker-compose.yml"))
