package se.jensim

import se.jensim.reflekt.RefleKt
import se.jensim.reflekt.RefleKtConf
import se.jensim.reflekt.internal.RefleKtImpl

/**
 * Sugar syntax for creating a plain all default version
 * @see RefleKtConf
 */
val refleKt: RefleKt get() = RefleKtImpl()

/**
 * RefleKt DSL version
 * @see RefleKtConf
 */
fun refleKt(conf: (RefleKtConf) -> Unit): RefleKt = RefleKtImpl(conf)

/**
 * All default version of reflekt
 * @see RefleKtConf
 */
fun refleKt(): RefleKt = RefleKtImpl()

/**
 * Full config version, that is Java friendly
 * @see RefleKtConf
 */
fun refleKt(conf: RefleKtConf): RefleKt = RefleKtImpl(conf)

/**
 * Shorthand version for setting only the package filter in reflekt configuration, other defaults then apply
 * @see RefleKtConf
 */
fun refleKt(pkgFilter: String): RefleKt = RefleKtImpl{
    packageFilter = pkgFilter
}


