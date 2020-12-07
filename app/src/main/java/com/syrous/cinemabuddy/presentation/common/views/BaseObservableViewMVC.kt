package com.syrous.cinemabuddy.presentation.common.views

import java.util.*
import kotlin.collections.HashSet

abstract class BaseObservableViewMVC<ListenerType>
    :BaseViewMVC(), ObservableViewMVC<ListenerType> {

    private val setOfListeners = HashSet<ListenerType>()

    override fun registerListener(listener: ListenerType) {
        setOfListeners.add(listener)
    }

    override fun unregisterListener(listener: ListenerType) {
        setOfListeners.remove(listener)
    }

    protected fun getListeners(): Set<ListenerType> = Collections.unmodifiableSet(setOfListeners)
}