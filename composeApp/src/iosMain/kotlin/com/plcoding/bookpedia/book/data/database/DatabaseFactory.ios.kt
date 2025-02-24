package com.plcoding.bookpedia.book.data.database

import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

actual class BookDatabaseFactory {
    actual fun create(): RoomDatabase.Builder<BookDatabase> {
        val dbFile = documentDirectory() + BookDatabase.DATABASE_NAME
        return Room.databaseBuilder<BookDatabase>(
            name = dbFile
        )
    }
    @OptIn(ExperimentalForeignApi::class)
    private fun documentDirectory(): String {
        val documentDirectory = NSFileManager.defaultManager
            .URLForDirectory(
                directory = NSDocumentDirectory,
                inDomain = NSUserDomainMask,
                appropriateForURL = null,
                create = false,
                error = null
            )
        return requireNotNull(documentDirectory?.path)

    }
}