package net.laggedhero.githubclient.domain

data class User(
    val name: UserName,
    val avatarUrl: UserAvatarUrl
)

inline class UserName(val value: String)
inline class UserAvatarUrl(val value: String)