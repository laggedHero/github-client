package net.laggedhero.githubclient.platform

import android.app.Application
import android.content.Context

internal class StringProviderImpl(application: Application) :
    StringProvider {

    private val context: Context = application.applicationContext

    override fun getString(id: Int): String {
        return context.getString(id)
    }
}