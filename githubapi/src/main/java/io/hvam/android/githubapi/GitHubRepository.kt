package io.hvam.android.githubapi

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import io.hvam.android.githubapi.model.RepoInfo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class GitHubRepository(
    private val api: GitHubApi,
    private val dispatcher: CoroutineDispatcher
) {

    suspend fun repositories(
        user: String
    ): Result<List<RepoInfo>, String> = withContext(dispatcher) {
        try {
            val response = api.repoList(user)
            if (response.isSuccessful) {
                response.body()?.let { Ok(it) } ?: Err("Unable to read response body")
            } else {
                Err(response.message() ?: "Unable to read error message")
            }
        } catch (ex: Throwable) {
            Err(ex.message ?: "Network failure")
        }
    }
}
