package com.varunbarad.tlog.external_services.local_database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.varunbarad.tlog.external_services.local_database.models.DbLogEntry
import io.reactivex.Completable
import io.reactivex.Observable

@Dao
interface LogEntryDao {
    @Insert
    fun insertLogEntry(entry: DbLogEntry): Completable

    @Query(
        """
        select id, category, startTime, endTime
        from LogEntries
        order by startTime desc
        """
    )
    fun getAllEntriesSortedReverseChronologicallyByStartTime(): Observable<List<DbLogEntry>>

    @Delete
    fun deleteEntry(entry: DbLogEntry): Completable
}
