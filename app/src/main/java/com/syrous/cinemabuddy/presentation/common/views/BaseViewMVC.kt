package com.syrous.cinemabuddy.presentation.common.views

import android.content.Context
import android.view.View
import androidx.annotation.StringRes

abstract class BaseViewMVC : ViewMVC {

    private lateinit var rootView: View

    override fun getRootView(): View = rootView

    protected fun setRootView(rootView: View) {
        this.rootView = rootView
    }

    protected open fun <T : View?> findViewById(id: Int): T {
        return getRootView().findViewById(id)
    }

    protected fun getContext(): Context = getRootView().context

    protected fun getString(@StringRes id: Int): String = getContext().getString(id)
}