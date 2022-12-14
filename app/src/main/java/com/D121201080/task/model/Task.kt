package com.D121201080.task.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "task_table")
class Task (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val judul: String,
    val isi: String,
    val kategori: String,
    val status: String
): Parcelable