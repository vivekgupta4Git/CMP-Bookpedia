package com.plcoding.bookpedia.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.plcoding.bookpedia.book.data.network.KtorRemoteBookDataSource
import com.plcoding.bookpedia.book.data.network.RemoteBookDataSource
import com.plcoding.bookpedia.book.domain.BookRepository
import com.plcoding.bookpedia.core.data.HttpClientFactory
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import com.plcoding.bookpedia.book.data.DefaultBookRepository
import com.plcoding.bookpedia.book.data.database.BookDatabase
import com.plcoding.bookpedia.book.data.database.BookDatabaseFactory
import com.plcoding.bookpedia.book.presentation.book_list.BookListViewModel
import com.plcoding.bookpedia.book.presentation.SelectedBookViewModel
import com.plcoding.bookpedia.book.presentation.book_detail.BookDetailViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf

expect val platformModule : Module

val sharedModule = module {
    single {
        HttpClientFactory.create(get())
    }
    single {
      get<BookDatabaseFactory>().create()
           .fallbackToDestructiveMigrationOnDowngrade(true)
           .setDriver(BundledSQLiteDriver())
           .setQueryCoroutineContext(Dispatchers.IO)
           .build()
    }
    single { get<BookDatabase>().dao }
    singleOf(::KtorRemoteBookDataSource).bind<RemoteBookDataSource>()
    singleOf(::DefaultBookRepository).bind<BookRepository>()

    viewModelOf(::BookListViewModel)
    viewModelOf(::SelectedBookViewModel)
    viewModelOf(::BookDetailViewModel)

}