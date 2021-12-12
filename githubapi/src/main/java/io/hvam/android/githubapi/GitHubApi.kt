package io.hvam.android.githubapi

import io.hvam.android.githubapi.model.RepoInfo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubApi {

    @GET("/orgs/{user}/repos")
    suspend fun repoList(
        @Path("user") user: String
    ): Response<List<RepoInfo>>
}
