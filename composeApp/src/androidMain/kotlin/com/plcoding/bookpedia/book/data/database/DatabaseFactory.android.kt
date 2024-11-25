package com.plcoding.bookpedia.book.data.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

actual class BookDatabaseFactory(
    private val context : Context
) {
    actual fun create(): RoomDatabase.Builder<BookDatabase> {
       val appContext = context.applicationContext
        val dbFile = appContext.getDatabasePath(BookDatabase.DATABASE_NAME)
        return Room.databaseBuilder<BookDatabase>(
            name = dbFile.absolutePath,
            context = appContext
        )
    }
}