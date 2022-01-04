package com.varunbarad.tlog.util

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

object ThreadSchedulers {
    val computation = Schedulers.computation()
    val io = Schedulers.io()
    val main = AndroidSchedulers.mainThread()
}
