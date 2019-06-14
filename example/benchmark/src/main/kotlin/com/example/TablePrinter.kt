package com.example

class TablePrinter<T>(private val data: List<T>) {

    private var sep: String = "|"
    private var pad: Char = ' '
    private var rBorder: String = "|"
    private var lBorder: String = "|"
    private val headers = mutableListOf<Pair<String, (T) -> String>>()

    fun with(separator: String = "|", padding:Char = ' ', leftBorder:String = "|", rightBorder:String = "|", headers: List<Pair<String, (T) -> String>>): TablePrinter<T> {
        sep = separator
        pad = padding
        rBorder = rightBorder
        lBorder = leftBorder
        this.headers.addAll(headers)
        return this
    }

    fun with(vararg headers: Pair<String, (T) -> String>): TablePrinter<T> = with(headers = headers.toList())

    fun asCsv(vararg headers: Pair<String, (T) -> String>): TablePrinter<T> = with(separator = ", ", leftBorder = "", rightBorder = "", padding = ' ', headers = headers.toList())

    fun print(): String {
        val matrix: List<List<String>> = listOf(headers.map { it.first }) +
                data.map { d: T -> headers.map { it.second(d) } }

        val max = (0 until headers.size).map { i ->
            matrix.map { it[i].length }.max()
        }

        return matrix.joinToString("\n") {
            it.mapIndexed { i, s -> s.padStart(max[i]!!) }
                    .joinToString(sep, lBorder, rBorder)
        }
    }
}


fun <T> List<T>.table() = TablePrinter(this)

