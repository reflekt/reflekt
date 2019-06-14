package com.example

class TablePrinter<T>(private val data: List<T>) {

    private var sep: String = "|"
    private var pad: Char = ' '
    private var rBorder: String = "|"
    private var lBorder: String = "|"
    private var floor: Char? = '‾'
    private var roof: Char? = '_'
    private val headers = mutableListOf<Pair<String, (T) -> String>>()

    fun with(separator: String = "|", padding: Char = ' ', leftBorder: String = "|", rightBorder: String = "|", headers: List<Pair<String, (T) -> String>>): TablePrinter<T> {
        sep = separator
        pad = padding
        rBorder = rightBorder
        lBorder = leftBorder
        this.headers.addAll(headers)
        return this
    }

    fun with(vararg headers: Pair<String, (T) -> String>): TablePrinter<T> = with(headers = headers.toList())

    fun asCsv(vararg headers: Pair<String, (T) -> String>): TablePrinter<T> = with(separator = ", ", leftBorder = "", rightBorder = "", padding = ' ', headers = headers.toList())

    override fun toString(): String {
        val matrix: List<List<String>> = listOf(headers.map { it.first }) +
                data.map { d: T -> headers.map { it.second(d) } }

        val max = (0 until headers.size).map { i ->
            matrix.map { it[i].length }.max()
        }

        var table = matrix.joinToString("\n") {
            it.mapIndexed { i, s -> s.padStart(max[i]!!) }
                    .joinToString(sep, lBorder, rBorder)
        }
        val width = table.lines().first().length
        if (roof != null) {
            table = "${roof?.toString()?.repeat(width)}\n$table"
        }
        if (floor != null) {
            table = "$table\n${floor?.toString()?.repeat(width)}"
        }
        return table
    }

    fun print() {
        print(toString())
    }

    fun println() {
        println(toString())
    }
}

fun <T> List<T>.table() = TablePrinter(this)

