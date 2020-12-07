package com.syrous.cinemabuddy.presentation.common.views

interface ObservableViewMVC<ListenerType> : ViewMVC {

    fun registerListener(listener: ListenerType)

    fun unregisterListener(listener: ListenerType)
}