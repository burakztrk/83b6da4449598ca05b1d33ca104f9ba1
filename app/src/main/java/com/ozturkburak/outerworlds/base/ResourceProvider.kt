package com.ozturkburak.outerworlds.base

import android.content.Context
import androidx.annotation.StringRes

class ResourcesProvider(private val context: Context) {

    fun getString(@StringRes restId: Int, vararg formatArgs: Any?) =
        context.getString(restId, formatArgs)
}