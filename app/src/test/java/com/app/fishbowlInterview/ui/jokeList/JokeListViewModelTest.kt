package com.app.fishbowlInterview.ui.jokeList

import app.cash.turbine.test
import com.app.fishbowlInterview.data.JokeRepository
import com.app.fishbowlInterview.data.models.Joke
import com.app.fishbowlInterview.data.models.JokeCategory
import com.app.fishbowlInterview.data.models.JokeError
import com.app.fishbowlInterview.data.models.JokeFlags
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class JokeListViewModelTest {
    private val jokeRepository = mockk<JokeRepository>()
    private val error = mockk<JokeError>()

    private val joke = Joke.SingleJoke(
        category = JokeCategory.PUN,
        flags = JokeFlags(
            nsfw = false,
            religious = false,
            political = false,
            racist = false,
            sexist = false,
            explicit = false
        ),
        id = 1,
        joke = "7 days without a pun makes one weak"
    )

    @Test
    fun `no new jokes found should pause pagination`() = runTest {
        // GIVEN
        coEvery {
            jokeRepository.fetchJokes(any(), any(), any())
        } returns listOf(joke)
        val viewModel = JokeListViewModel(jokeRepository)
        val expected = JokeListUiState(
            jokes = listOf(joke),
            isLoading = false,
            currentFilter = JokeCategory.ANY,
            currentSearchTerm = null,
            errorMessage = null,
            pausePagination = true
        )

        viewModel.uiState.test {
            awaitItem()
            // WHEN
            viewModel.fetchJokes()

            // THEN
            val actual = awaitItem()
            assert(actual.jokes == expected.jokes)
            assert(actual.isLoading == expected.isLoading)
            assert(actual.errorMessage == expected.errorMessage)
            assert(actual.pausePagination == expected.pausePagination)
        }
    }

    @Test
    fun `new jokes found shouldn't pause pagination`() = runTest {
        // GIVEN
        coEvery {
            jokeRepository.fetchJokes(any(), any(), any())
        } returns listOf(joke) andThen listOf(joke.copy(id = 2))
        val viewModel = JokeListViewModel(jokeRepository)
        val expected = JokeListUiState(
            jokes = listOf(joke),
            isLoading = false,
            currentFilter = JokeCategory.ANY,
            currentSearchTerm = null,
            errorMessage = null,
            pausePagination = false
        )

        viewModel.uiState.test {
            // WHEN
            viewModel.fetchMoreItems()

            // THEN
            assert(awaitItem().isLoading)
            val actual = awaitItem()
            assert(actual.jokes.size == 2)
            assert(actual.isLoading == expected.isLoading)
            assert(actual.errorMessage == expected.errorMessage)
            assert(actual.pausePagination == expected.pausePagination)
        }
    }

    @Test
    fun `errors should pause pagination`() = runTest {
        // GIVEN
        coEvery {
            jokeRepository.fetchJokes(any(), any(), any())
        } returns null
        every {
            error.message
        } returns "foo"
        val viewModel = JokeListViewModel(jokeRepository)
        val expected = JokeListUiState(
            jokes = listOf(),
            isLoading = false,
            currentFilter = JokeCategory.ANY,
            currentSearchTerm = null,
            errorMessage = "Error: foo",
            pausePagination = true
        )

        viewModel.uiState.test {
            awaitItem()
            // WHEN
            viewModel.handleError(error)

            // THEN
            val actual = awaitItem()
            assert(actual.jokes == expected.jokes)
            assert(actual.isLoading == expected.isLoading)
            assert(actual.errorMessage == expected.errorMessage)
            assert(actual.pausePagination == expected.pausePagination)
        }
    }
}