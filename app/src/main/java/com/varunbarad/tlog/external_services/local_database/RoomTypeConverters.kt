package com.varunbarad.tlog.external_services.local_database

import androidx.room.TypeConverter
import com.varunbarad.tlog.models.EntryCategory
import java.time.LocalDateTime
import java.util.UUID

class RoomTypeConverters {
    @TypeConverter
    fun toLocalDateTime(value: String?): LocalDateTime? {
        return value?.let { LocalDateTime.parse(it) }
    }

    @TypeConverter
    fun fromLocalDateTime(value: LocalDateTime?): String? {
        return value?.toString()
    }

    @TypeConverter
    fun toEntryCategory(value: String?): EntryCategory? {
        return value?.let { EntryCategory.valueOf(it) }
    }

    @TypeConverter
    fun fromEntryCategory(value: EntryCategory?): String? {
        return value?.name
    }

    @TypeConverter
    fun toUUID(value: String?): UUID? {
        return value?.let { UUID.fromString(it) }
    }

    @TypeConverter
    fun fromUUID(value: UUID?): String? {
        return value?.toString()
    }
}
