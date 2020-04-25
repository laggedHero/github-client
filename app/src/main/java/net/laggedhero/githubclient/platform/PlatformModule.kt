package net.laggedhero.githubclient.platform

import android.app.Application
import dagger.Module
import dagger.Provides

@Module
object PlatformModule {
    @Provides
    fun providesSchedulerProvider(): SchedulerProvider {
        return SchedulerProviderImpl()
    }

    @Provides
    fun providesStringProvider(application: Application): StringProvider {
        return StringProviderImpl(
            application
        )
    }
}