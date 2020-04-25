package net.laggedhero.githubclient.data

import io.reactivex.Single
import net.laggedhero.githubclient.data.dto.RepositoryDto
import net.laggedhero.githubclient.data.dto.SearchRepositoryDto
import net.laggedhero.githubclient.data.dto.UserDto
import net.laggedhero.githubclient.domain.*

class GitHubRepositoryImpl(private val gitHubApi: GitHubApi) : GitHubRepository {
    override fun search(query: String): Single<List<Repository>> {
        return gitHubApi.searchRepositories(query)
            .map { it.toRepositoryList() }
    }

    private fun SearchRepositoryDto.toRepositoryList(): List<Repository> {
        return items.map { it.toRepository() }
    }

    private fun RepositoryDto.toRepository(): Repository {
        return Repository(
            name = RepositoryName(full_name),
            description = RepositoryDescription(description.orEmpty()),
            owner = owner.toUser()
        )
    }

    private fun UserDto.toUser(): User {
        return User(
            UserName(login),
            UserAvatarUrl(avatar_url)
        )
    }
}