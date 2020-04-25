package net.laggedhero.githubclient.domain

import io.reactivex.Single

interface GitHubRepository {
    fun search(query: String): Single<List<Repository>>
}