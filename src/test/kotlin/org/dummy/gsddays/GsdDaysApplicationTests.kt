package org.dummy.gsddays

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest
class GsdDaysApplicationTests {
    @Test
    fun it_should_succeed() {
        assertThat(true).isTrue()
    }
}
