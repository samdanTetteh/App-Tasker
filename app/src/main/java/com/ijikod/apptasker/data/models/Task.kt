package com.ijikod.apptasker.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "Tasks")
data class Task(
        @PrimaryKey
        val id : String = UUID.randomUUID().toString(),
        val title : String = "",
        val description: String = "",
        val completed : Boolean = false,
        val createdDate: String = "",
)