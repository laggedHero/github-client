package net.laggedhero.githubclient.domain

data class Repository(
    val name: RepositoryName,
    val owner: User,
    val description: RepositoryDescription
)

inline class RepositoryName(val value: String)
inline class RepositoryDescription(val value: String)