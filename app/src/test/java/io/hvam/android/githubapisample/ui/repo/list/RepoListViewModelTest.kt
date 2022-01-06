package io.hvam.android.githubapisample.ui.repo.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import io.hvam.android.githubapi.GitHubRepository
import io.hvam.android.githubapi.model.RepoInfo
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import org.junit.Rule
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.get
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertNull
import kotlin.test.assertSame
import kotlin.test.assertTrue

class RepoListViewModelTest : KoinTest {

    private val mockRepoInfo = mockk<RepoInfo>(relaxed = true)
    private val stubResultOk = Ok(listOf(mockRepoInfo))
    private val stubResultErr = Err("failed")
    private val mockGitHubRepository = mockk<GitHubRepository>(relaxed = true)
    private val mockLifecycleOwner = mockk<LifecycleOwner>(relaxed = true)
    private val testModule = module {
        viewModel { RepoListViewModel() }
        single { mockGitHubRepository }
    }

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @BeforeTest
    fun before() {
        // We'll need to tell koin which mocks to inject during this test.
        startKoin {
            modules(testModule)
        }
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `test load data from github and update UI with success result`() {
        // setup mocking
        coEvery { mockGitHubRepository.repositories(any()) } returns stubResultOk
        val mockObserver = mockk<Observer<UiState>>(relaxed = true)
        // Get subject under test from dependency framework
        val sut = get<RepoListViewModel>()
        sut.uiState.observeForever(mockObserver)
        sut.onCreate(mockLifecycleOwner)
        // Verify that we fetch data from the github repo
        coVerify { mockGitHubRepository.repositories(any()) }
        // And verify that the data is posted to the live data, and sent to any one observing (data binding)
        verify { mockObserver.onChanged(any()) }
        assertTrue { sut.uiState.value is UiState.Success }
    }

    @Test
    fun `test load data from github and update UI with error result`() {
        // setup mocking
        coEvery { mockGitHubRepository.repositories(any()) } returns stubResultErr
        val mockObserver = mockk<Observer<UiState>>(relaxed = true)
        // Get subject under test from dependency framework
        val sut = get<RepoListViewModel>()
        sut.uiState.observeForever(mockObserver)
        sut.onCreate(mockLifecycleOwner)
        // Verify that we fetch data from the github repo
        coVerify { mockGitHubRepository.repositories(any()) }
        // And verify that the data is posted to the live data, and sent to any one observing (data binding)
        verify { mockObserver.onChanged(any()) }
        assertTrue { sut.uiState.value is UiState.Error }
    }

    /**
     * A view (Fragment or Activity) shouldn't be allowed to determine if a given click should
     * result in a specific action. A click could potentially be ignored if the repository is private
     * and determining that is the job of the ViewModel (aka business logic).
     *
     * Therefore we've setup the ViewModel to handle the click and update a live data if needed,
     * indicating the Fragment should transition to a new view.
     */
    @Test
    fun `test click to show details`() {
        // Setup mocking
        val mockObserver = mockk<Observer<RepoInfo?>>(relaxed = true)
        // Get subject under test from dependency framework
        val sut = get<RepoListViewModel>()
        sut.showDetails.observeForever(mockObserver)
        assertNull(sut.showDetails.value)
        sut.onClick.onItemClicked(mockRepoInfo)
        assertSame(mockRepoInfo, sut.showDetails.value)
        // Before transitioning we'll reset the LiveData so we do not end up in a loop when navigating back
        sut.onPause(mockLifecycleOwner)
        assertNull(sut.showDetails.value)
    }
}
