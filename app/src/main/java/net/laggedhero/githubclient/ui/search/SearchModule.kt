package net.laggedhero.githubclient.ui.search

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import net.laggedhero.githubclient.domain.GitHubRepository
import net.laggedhero.githubclient.injection.fragment.FragmentKey
import net.laggedhero.githubclient.injection.viewmodel.ViewModelKey
import net.laggedhero.githubclient.platform.SchedulerProvider
import net.laggedhero.githubclient.platform.StringProvider

@Module
object SearchModule {
    @Provides
    @IntoMap
    @FragmentKey(SearchFragment::class)
    fun providesSearchFragment(
        viewModelFactory: ViewModelProvider.Factory,
        schedulerProvider: SchedulerProvider
    ): Fragment {
        return SearchFragment(viewModelFactory, schedulerProvider)
    }

    @Provides
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    fun providesSearchViewModel(
        gitHubRepository: GitHubRepository,
        schedulerProvider: SchedulerProvider,
        stringProvider: StringProvider
    ): ViewModel {
        return SearchViewModel(gitHubRepository, schedulerProvider, stringProvider)
    }
}