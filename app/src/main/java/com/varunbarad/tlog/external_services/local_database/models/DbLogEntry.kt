package com.varunbarad.tlog.external_services.local_database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.varunbarad.tlog.models.EntryCategory
import java.time.LocalDateTime
import java.util.UUID

@Entity(tableName = "LogEntries")
data class DbLogEntry(
    @PrimaryKey @ColumnInfo(name = "id") val id: UUID,
    @ColumnInfo(name = "category") val category: EntryCategory,
    @ColumnInfo(name = "startTime") val startTime: LocalDateTime,
    @ColumnInfo(name = "endTime") val endTime: LocalDateTime,
)
