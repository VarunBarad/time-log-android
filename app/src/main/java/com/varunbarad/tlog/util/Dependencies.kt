package com.varunbarad.tlog.util

import android.content.Context
import androidx.room.Room
import com.varunbarad.tlog.external_services.local_database.TimeLogDatabase
import com.varunbarad.tlog.repositories.LogEntryRepository

object Dependencies {
    private lateinit var timeLogDatabase: TimeLogDatabase

    fun getTimeLogDatabase(context: Context): TimeLogDatabase {
        synchronized(this) {
            if (this::timeLogDatabase.isInitialized.not()) {
                this.timeLogDatabase = Room.databaseBuilder(
                    context.applicationContext,
                    TimeLogDatabase::class.java,
                    TimeLogDatabase.databaseName,
                ).build()
            }
        }

        return this.timeLogDatabase
    }

    fun getLogEntryRepository(context: Context): LogEntryRepository {
        return LogEntryRepository(this.getTimeLogDatabase(context).logEntryDao())
    }
}
