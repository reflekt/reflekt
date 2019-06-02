package se.jensim.reflekt.usage

import org.junit.Test
import se.jensim.refleKt
import kotlin.test.assertNotEquals

class KotlinRefleKtUsageTest {

    @Test
    fun `use reflekt`() {
        assertNotEquals(refleKt.getAllTypes(), emptySet())
    }
}
