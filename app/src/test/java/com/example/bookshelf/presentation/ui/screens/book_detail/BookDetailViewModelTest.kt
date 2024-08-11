package com.example.bookshelf.presentation.ui.screens.book_detail

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
import assertk.assertions.isTrue
import com.example.bookshelf.data.remote.mappers.toBook
import com.example.bookshelf.data.repository.FakeBookRepository
import com.example.bookshelf.data.repository.FakeFavoriteRepository
import com.example.bookshelf.data.source.FakeBookData.bookItem
import com.example.bookshelf.domain.model.Book
import com.example.bookshelf.util.MainCoroutineExtension
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MainCoroutineExtension::class)
class BookDetailViewModelTest {
    private lateinit var viewModel: BookDetailViewModel
    private lateinit var bookRepository: FakeBookRepository
    private lateinit var favoriteRepository: FakeFavoriteRepository
    @BeforeEach
    fun setUp() {
        bookRepository = FakeBookRepository()
        favoriteRepository = FakeFavoriteRepository()
        viewModel = BookDetailViewModel(bookRepository, favoriteRepository)
    }
    @Test
    fun `Test book state flow`() = runTest {
        viewModel.bookState.test {
            awaitItem()
            viewModel.getBook("a")
            val emission1 = awaitItem()
            assertThat(emission1).isEqualTo(
                BookDetailState(
                    book = null,
                    isFavorite = false,
                    isLoading = true,
                    error = null
                )
            )
            val emission2 = awaitItem()
            assertThat(emission2).isEqualTo(
                BookDetailState(
                    book = bookItem.toBook(),
                    isFavorite = false,
                    isLoading = false,
                    error = null
                )
            )
            viewModel.toggleFavorite()
            val emission3 = awaitItem()
            assertThat(emission3.isFavorite).isTrue()
            viewModel.toggleFavorite()
            val emission4 = awaitItem()
            assertThat(emission4.isFavorite).isFalse()
        }
    }
    @Test
    fun `Test book state flow fails to find book`() = runTest {
        bookRepository.cannotFindBook = true
        viewModel.bookState.test {
            awaitItem()
            viewModel.getBook("a")
            awaitItem()
            val emission2 = awaitItem()
            assertThat(emission2).isEqualTo(
                BookDetailState(
                    book = null,
                    isFavorite = false,
                    isLoading = false,
                    error = "Book not found"
                )
            )
        }
    }
    @Test
    fun `Test book state flow network error`() = runTest {
        bookRepository.shouldReturnNetworkError = true
        viewModel.bookState.test {
            awaitItem()
            viewModel.getBook("a")
            awaitItem()
            val emission2 = awaitItem()
            assertThat(emission2).isEqualTo(
                BookDetailState(
                    book = null,
                    isFavorite = false,
                    isLoading = false,
                    error = "Network Error"
                )
            )
        }
    }
}