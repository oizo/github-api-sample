import org.gradle.api.Project
import java.io.FileInputStream
import java.util.*

class GitHubProperties(
    private val rootProject: Project
) {

    companion object {
        const val KEY_USER = "github_user"
        const val KEY_PAT = "github_pat"
        const val KEY_ORGANIZATION = "github_organization"
        const val LOCAL_PROPERTIES = "local.properties"
    }
    private val localProperties = Properties().apply {
        load(FileInputStream(rootProject.file(LOCAL_PROPERTIES)))
    }
    private fun get(key: String): String = try {
        localProperties[key] as String
    } catch (e: Exception) {
        throw IllegalStateException("$key not defined in $LOCAL_PROPERTIES")
    }
    val user: String = get(KEY_USER)
    val pat: String = get(KEY_PAT)
    val org: String = get(KEY_ORGANIZATION)
}