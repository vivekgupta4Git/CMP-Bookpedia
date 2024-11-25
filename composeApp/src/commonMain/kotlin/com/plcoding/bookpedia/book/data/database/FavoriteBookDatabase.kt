package com.plcoding.bookpedia.book.data.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.plcoding.bookpedia.book.data.database.BookDatabase.Companion.DATABASE_VERSION

@ConstructedBy(BookDatabaseConstructor::class)
@Database(
    entities = [BookEntity::class],
    version = DATABASE_VERSION
)
@TypeConverters(StringListTypeConverter::class)
abstract class BookDatabase: RoomDatabase() {
    abstract val dao: FavoriteBokDao

    companion object {
        const val DATABASE_NAME = "book_db"
        const val DATABASE_VERSION = 1
    }
}