package com.example.bookshelf.data.local

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.doesNotContain
import assertk.assertions.isFalse
import assertk.assertions.isTrue
import com.example.bookshelf.data.local.favorite.Favorite
import com.example.bookshelf.data.local.favorite.FavoriteDao
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FavoriteDaoTest {
    private lateinit var database: BookDatabase
    private lateinit var dao: FavoriteDao
    private val favorite = Favorite(
        "aaa",
        "Triple A",
        "aaa.url"
    )
    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            BookDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.favoriteDao()
    }
    @After
    fun teardown() {
        database.close()
    }
    @Test
    fun insertFavorite_databaseContains() = runTest {
        dao.addFavorite(favorite)
        val allFavorites = dao.getAllFavorites().first()
        assertThat(allFavorites).contains(favorite)
    }
    @Test
    fun deleteFavorite_databaseDoesNotContain() = runTest {
        dao.addFavorite(favorite)
        dao.deleteFavorite(favorite.id)
        val allFavorites = dao.getAllFavorites().first()
        assertThat(allFavorites).doesNotContain(favorite)
    }
    @Test
    fun addFavoriteIsFavorite_returnsTrue() = runTest {
        dao.addFavorite(favorite)
        val isFavorite = dao.isFavorite(favorite.id).first()
        assertThat(isFavorite).isTrue()
    }
    @Test
    fun isNotFavorite_returnsFalse() = runTest {
        val isFavorite = dao.isFavorite(favorite.id).first()
        assertThat(isFavorite).isFalse()
    }
}