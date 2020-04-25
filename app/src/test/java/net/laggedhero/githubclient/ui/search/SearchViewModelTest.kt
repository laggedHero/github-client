package net.laggedhero.githubclient.ui.search

import io.reactivex.Single
import io.reactivex.schedulers.TestScheduler
import net.laggedhero.githubclient.domain.*
import net.laggedhero.githubclient.platform.FakeSchedulerProvider
import net.laggedhero.githubclient.platform.FakeStringProvider
import org.junit.Test

class SearchViewModelTest {

    @Test
    fun `given no search when subscribing then delivers an initial state`() {
        // given
        val repository = FakeGitHubRepository(Single.error(Throwable("Error")))
        val schedulerProvider = FakeSchedulerProvider()
        val stringProvider = FakeStringProvider { "Error" }

        // when
        val sut = SearchViewModel(
            repository, schedulerProvider, stringProvider
        )

        // then
        sut.state.test()
            .assertValue(
                SearchState(false, null, null)
            )
    }

    @Test
    fun `given repository has not returned when searching then delivers a loading state`() {
        // given
        val repository = FakeGitHubRepository(Single.error(Throwable("Error")))
        val testScheduler = TestScheduler()
        val schedulerProvider = FakeSchedulerProvider(
            testScheduler
        )
        val stringProvider = FakeStringProvider { "Error" }

        // when
        val sut = SearchViewModel(
            repository, schedulerProvider, stringProvider
        )

        sut.search("some string")

        // then
        sut.state.test().assertValue(
            SearchState(true, null, null)
        )
    }

    @Test
    fun `given a repository error when searching then delivers a state with error`() {
        // given
        val repository = FakeGitHubRepository(Single.error(Throwable("Error")))
        val schedulerProvider = FakeSchedulerProvider()
        val stringProvider = FakeStringProvider { "Error" }

        // when
        val sut = SearchViewModel(
            repository, schedulerProvider, stringProvider
        )

        sut.search("some string")

        // then
        sut.state.test()
            .assertValue(
                SearchState(false, null, "Error")
            )
    }

    @Test
    fun `given a repository response when searching then delivers a state with content`() {
        // given
        val content = listOf(
            Repository(
                RepositoryName("RepositoryName 01"),
                User(
                    UserName("UserName 01"),
                    UserAvatarUrl("AvatarUrl 01")
                ),
                RepositoryDescription("RepositoryDescription 01")
            )
        )
        val repository = FakeGitHubRepository(Single.just(content))
        val schedulerProvider = FakeSchedulerProvider()
        val stringProvider = FakeStringProvider { "Error" }

        // when
        val sut = SearchViewModel(
            repository, schedulerProvider, stringProvider
        )

        sut.search("some string")

        // then
        sut.state.test()
            .assertValue(
                SearchState(false, content, null)
            )
    }
}