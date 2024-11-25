package com.plcoding.bookpedia.book.data.database

import androidx.room.RoomDatabase

expect class BookDatabaseFactory{
    fun create() : RoomDatabase.Builder<BookDatabase>
}