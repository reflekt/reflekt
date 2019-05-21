package se.jensim.reflekt

import org.junit.Assert


class RefleKtTest {
	
	fun `we are able to instantiate the class`(){
		val target = RefleKt()
		Assert.assertNotNull(target)
	}
}

