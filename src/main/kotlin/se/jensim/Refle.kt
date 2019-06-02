package se.jensim

import se.jensim.reflekt.RefleKt
import se.jensim.reflekt.RefleKtConf
import se.jensim.reflekt.RefleKtImpl

val refleKt: RefleKt get() = RefleKtImpl()

fun refleKt(conf:(RefleKtConf) ->Unit):RefleKt = RefleKtImpl(conf)
fun refleKt():RefleKt = RefleKtImpl()
fun refleKt(conf: RefleKtConf):RefleKt = RefleKtImpl(conf)


