package net.laggedhero.githubclient

import android.app.Application
import net.laggedhero.githubclient.injection.AppComponent
import net.laggedhero.githubclient.injection.DaggerAppComponent

class GitHubClientApplication : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .application(this)
            .build()
    }
}