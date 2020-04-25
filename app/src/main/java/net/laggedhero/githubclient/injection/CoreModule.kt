package net.laggedhero.githubclient.injection

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import net.laggedhero.githubclient.injection.fragment.DaggerFragmentFactory
import net.laggedhero.githubclient.injection.viewmodel.DaggerViewModelFactory
import javax.inject.Provider

@Module
object CoreModule {
    @Provides
    fun providesDaggerFragmentFactory(creator: Map<Class<out Fragment>, @JvmSuppressWildcards Provider<Fragment>>): FragmentFactory {
        return DaggerFragmentFactory(creator)
    }

    @Provides
    fun providesDaggerViewModelFactory(creator: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>): ViewModelProvider.Factory {
        return DaggerViewModelFactory(creator)
    }
}