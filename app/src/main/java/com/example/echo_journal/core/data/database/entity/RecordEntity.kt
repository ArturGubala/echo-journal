package com.example.echo_journal.core.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "records")
class RecordEntity {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
