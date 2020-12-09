package com.syrous.cinemabuddy.utils

import java.util.*
import java.util.concurrent.ConcurrentHashMap

abstract class BaseObservable<ListenerClass> {

    private val listeners = Collections.newSetFromMap(
        ConcurrentHashMap<ListenerClass, Boolean>(1))

    fun registerListener(listener: ListenerClass) {
        listeners.add(listener)
    }

    fun unRegisterListener(listener: ListenerClass) {
        listeners.remove(listener)
    }

    protected fun getListeners(): Set<ListenerClass> =
        Collections.unmodifiableSet(listeners)
}