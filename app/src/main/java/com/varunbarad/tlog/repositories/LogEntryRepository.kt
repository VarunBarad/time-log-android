package com.varunbarad.tlog.repositories

import com.varunbarad.tlog.external_services.local_database.LogEntryDao
import com.varunbarad.tlog.external_services.local_database.models.DbLogEntry
import com.varunbarad.tlog.util.ThreadSchedulers
import io.reactivex.Completable
import io.reactivex.Observable
import kotlin.math.log

class LogEntryRepository(
    private val logEntryDao: LogEntryDao,
) {
    fun insertNewLogEntry(logEntry: DbLogEntry): Completable {
        return logEntryDao.insertLogEntry(entry = logEntry)
            .subscribeOn(ThreadSchedulers.io)
            .observeOn(ThreadSchedulers.main)
    }

    fun getAllEntriesSortedReverseChronologicallyByStartTime(): Observable<List<DbLogEntry>> {
        return logEntryDao.getAllEntriesSortedReverseChronologicallyByStartTime()
            .subscribeOn(ThreadSchedulers.io)
            .observeOn(ThreadSchedulers.main)
    }

    fun deleteEntry(entry: DbLogEntry): Completable {
        return logEntryDao.deleteEntry(entry = entry)
            .subscribeOn(ThreadSchedulers.io)
            .observeOn(ThreadSchedulers.main)
    }
}
