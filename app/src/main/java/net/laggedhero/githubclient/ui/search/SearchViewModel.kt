package net.laggedhero.githubclient.ui.search

import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject
import net.laggedhero.githubclient.R
import net.laggedhero.githubclient.domain.GitHubRepository
import net.laggedhero.githubclient.platform.SchedulerProvider
import net.laggedhero.githubclient.platform.StringProvider

class SearchViewModel(
    private val gitHubRepository: GitHubRepository,
    private val schedulerProvider: SchedulerProvider,
    private val stringProvider: StringProvider
) : ViewModel() {

    private var disposable: Disposable? = null

    private val behaviorSubject = BehaviorSubject.createDefault(SearchState(false, null, null))
    val state: Observable<SearchState>
        get() = behaviorSubject

    fun search(query: String) {
        disposable?.dispose()
        disposable = gitHubRepository.search(query)
            .subscribeOn(schedulerProvider.io())
            .toObservable()
            .map { SearchState(false, it, null) }
            .onErrorReturn {
                SearchState(false, null, stringProvider.getString(R.string.app_generic_error))
            }
            .startWith(loadingState())
            .subscribe { uiState -> behaviorSubject.onNext(uiState) }
    }

    private fun loadingState(): SearchState {
        return behaviorSubject.value
            ?.copy(loading = true, error = null)
            ?: SearchState(true, null, null)
    }

    override fun onCleared() {
        disposable?.dispose()
        super.onCleared()
    }
}