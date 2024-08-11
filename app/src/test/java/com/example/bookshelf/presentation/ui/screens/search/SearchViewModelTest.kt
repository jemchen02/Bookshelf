package com.example.bookshelf.presentation.ui.screens.search

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.example.bookshelf.data.repository.FakeBookRepository
import com.example.bookshelf.data.repository.FakeFavoriteRepository
import com.example.bookshelf.data.source.FakeFavoriteData
import com.example.bookshelf.util.MainCoroutineExtension
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import app.cash.turbine.test
import assertk.assertions.isEmpty
import assertk.assertions.isNotNull
import com.example.bookshelf.data.source.FakeBookData

@ExtendWith(MainCoroutineExtension::class)
class SearchViewModelTest {
    private lateinit var viewModel: SearchViewModel
    private lateinit var bookRepository: FakeBookRepository
    private lateinit var favoriteRepository: FakeFavoriteRepository
    @BeforeEach
    fun setUp() {
        bookRepository = FakeBookRepository()
        favoriteRepository = FakeFavoriteRepository()
        viewModel = SearchViewModel(bookRepository,favoriteRepository)
    }
    @Test
    fun `Test edit search text flow`() = runTest{
        viewModel.searchState.test {
            val emission1 = awaitItem()
            assertThat(emission1.searchText).isEmpty()

            viewModel.changeSearchText("New search")
            val emission2 = awaitItem()
            assertThat(emission2.searchText).isEqualTo("New search")
        }
    }
    @Test
    fun `Test search empty string flow`() = runTest{
        viewModel.searchState.test {
            awaitItem()
            viewModel.changeSearchText("a")
            awaitItem()
            viewModel.getBooks()
            awaitItem()
            val emission4 = awaitItem()
            assertThat(emission4.searchResults).isNotNull()
            viewModel.changeSearchText("")
            viewModel.getBooks()
            val emission5 = awaitItem()
            assertThat(emission5).isEqualTo(SearchState())
        }
    }

    @Test
    fun `Test search query flow`() = runTest {
        viewModel.searchState.test {
            awaitItem()
            viewModel.changeSearchText("a")
            awaitItem()
            viewModel.getBooks()
            awaitItem()
            val emission4 = awaitItem()
            assertThat(emission4).isEqualTo(SearchState(
                searchText = "",
                searchResults = FakeBookData.bookPreviews,
                isLoading = false,
                error = null
            ))
        }
    }

    @Test
    fun `Test search with network error flow`() = runTest {
        bookRepository.shouldReturnNetworkError = true
        viewModel.searchState.test {
            awaitItem()
            viewModel.changeSearchText("a")
            awaitItem()
            viewModel.getBooks()
            awaitItem()
            val emission4 = awaitItem()
            assertThat(emission4).isEqualTo(SearchState(
                searchText = "",
                searchResults = null,
                isLoading = false,
                error = "Network Error"
            ))
        }
    }

    @Test
    fun `Get all favorites returns favorites`() = runTest {
        val result = viewModel.getAllFavorites().first()
        assertThat(result).isEqualTo(FakeFavoriteData.favoriteList)
    }
}