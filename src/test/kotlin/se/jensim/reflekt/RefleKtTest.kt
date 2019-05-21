package se.jensim.reflekt

import kotlin.test.Test
import kotlin.test.assertNotNull

class RefleKtTest {

	@Test
	fun `we are able to instantiate the class`(){
		val target = RefleKt().findClass()
		assertNotNull(target)
	}
}

