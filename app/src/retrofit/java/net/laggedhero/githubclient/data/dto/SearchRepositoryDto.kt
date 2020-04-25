package net.laggedhero.githubclient.data.dto

data class SearchRepositoryDto(
    val items: List<RepositoryDto>
)

data class RepositoryDto(
    val full_name: String,
    val description: String?,
    val owner: UserDto
)

data class UserDto(
    val login: String,
    val avatar_url: String
)