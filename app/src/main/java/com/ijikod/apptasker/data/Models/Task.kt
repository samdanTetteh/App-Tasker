package com.ijikod.apptasker.data.Models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Tasks")
data class Task(
        @PrimaryKey
        val id : Int,
        val title : String,
        val color : String,
        val completed : Boolean,
        val icon : Int
)