package net.laggedhero.githubclient.ui.search

import net.laggedhero.githubclient.domain.Repository

data class SearchState(
    val loading: Boolean,
    val repositoryList: List<Repository>?,
    val error: String?
)