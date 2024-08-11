package com.example.bookshelf.presentation.ui.favorite

import androidx.lifecycle.ViewModel
import com.example.bookshelf.domain.model.BookPreview
import com.example.bookshelf.domain.repository.FavoriteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val favoriteRepository: FavoriteRepository
): ViewModel(){
    fun getAllFavorites(): Flow<List<BookPreview>> = favoriteRepository.getAllFavoritesStream()
}