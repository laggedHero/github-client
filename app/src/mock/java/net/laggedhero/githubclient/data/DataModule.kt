package net.laggedhero.githubclient.data

import dagger.Module
import dagger.Provides
import net.laggedhero.githubclient.domain.GitHubRepository

@Module
object DataModule {
    @Provides
    fun providesGitHubRepository(): GitHubRepository {
        return GitHubRepositoryImpl()
    }
}