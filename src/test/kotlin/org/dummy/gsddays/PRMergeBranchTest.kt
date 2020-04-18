package org.dummy.gsddays

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class PRMergeBranchTest {
    @Test
    fun it_should_match() {
        assertThat(PRMergeBranch().value).isEqualTo(42)
    }
}
