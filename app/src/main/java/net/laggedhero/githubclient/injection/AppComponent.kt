package net.laggedhero.githubclient.injection

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import net.laggedhero.githubclient.MainActivity
import net.laggedhero.githubclient.data.DataModule
import net.laggedhero.githubclient.platform.PlatformModule
import net.laggedhero.githubclient.ui.search.SearchModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        PlatformModule::class,
        DataModule::class,
        CoreModule::class,
        SearchModule::class
    ]
)
interface AppComponent {
    fun inject(mainActivity: MainActivity)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}