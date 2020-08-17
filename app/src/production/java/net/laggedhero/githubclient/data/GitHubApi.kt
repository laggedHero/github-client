package net.laggedhero.githubclient.data

import io.reactivex.Single
import net.laggedhero.githubclient.data.dto.SearchRepositoryDto
import retrofit2.http.GET
import retrofit2.http.Query

interface GitHubApi {
    @GET("search/repositories")
    fun searchRepositories(@Query("q") query: String): Single<SearchRepositoryDto>
}