package com.ijikod.apptasker.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "Tasks")
data class Task(
        @PrimaryKey
        val id : Int,
        val title : String,
        val color : String,
        val completed : Boolean,
        val icon : Int,
        val createdDate: Date,
        val completeDate: Date
)