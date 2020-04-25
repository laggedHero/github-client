package net.laggedhero.githubclient.data

import io.reactivex.Single
import net.laggedhero.githubclient.domain.*

class GitHubRepositoryImpl : GitHubRepository {

    private val mockedSearchResults = List(10) {
        Repository(
            name = RepositoryName("Mocked Repository $it"),
            owner = User(
                name = UserName("Mocked User $it"),
                avatarUrl = UserAvatarUrl("https://picsum.photos/200")
            ),
            description = RepositoryDescription("Mocked description $it")
        )
    }

    override fun search(query: String): Single<List<Repository>> {
        return Single.just(mockedSearchResults)
    }
}