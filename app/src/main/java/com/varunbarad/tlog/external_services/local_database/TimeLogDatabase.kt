package com.varunbarad.tlog.external_services.local_database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.varunbarad.tlog.external_services.local_database.models.DbLogEntry

@Database(
    entities = [
        DbLogEntry::class,
    ],
    version = TimeLogDatabase.databaseVersion,
    exportSchema = false,
)
@TypeConverters(RoomTypeConverters::class)
abstract class TimeLogDatabase : RoomDatabase() {
    abstract fun logEntryDao(): LogEntryDao

    companion object {
        const val databaseVersion = 1
        const val databaseName = "TimeLog-Database"
    }
}
