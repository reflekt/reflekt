package com.example

import org.hamcrest.Matchers
import org.junit.Assert.assertThat
import org.junit.Test

class TablePrinterKtTest {

    @Test
    fun testPrintTable() {
        // given
        val datas = listOf(
                MyData("1", "1"),
                MyData("2", "2"),
                MyData("3", "3"),
                MyData("4", "5")
        )

        // when
        val result = datas.table()
                .with("one" to MyData::onne, "two_b" to MyData::twwo)
                .toString()

        // then
        val expected = """
            ___________
            |one|two_b|
            |  1|    1|
            |  2|    2|
            |  3|    3|
            |  4|    5|
            ‾‾‾‾‾‾‾‾‾‾‾
        """.trimIndent()
        assertThat(result, Matchers.equalTo(expected))
    }

    private data class MyData(val onne: String, val twwo: String)
}
