package net.laggedhero.githubclient.domain

import io.reactivex.Single

class FakeGitHubRepository(
    private val result: Single<List<Repository>>
) : GitHubRepository {
    override fun search(query: String): Single<List<Repository>> {
        return result
    }
}