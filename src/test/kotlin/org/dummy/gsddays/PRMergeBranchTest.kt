package org.dummy.gsddays

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class PRMergeBranchTest {
    @Test
    internal fun `it should be greater than 0`() {
        assertThat(PRMergeBranch().isGreaterThanZero()).isTrue()
    }
}
