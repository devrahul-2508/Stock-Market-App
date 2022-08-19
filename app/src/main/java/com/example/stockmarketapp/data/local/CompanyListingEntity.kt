package com.example.stockmarketapp.data.local

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "companylistingentity")
data class CompanyListingEntity(

    @ColumnInfo(name = "name")
    val name : String,
    @ColumnInfo(name = "symbol")
    val symbol : String,
    @ColumnInfo(name = "exchange")
    val exchange : String,
    @PrimaryKey(autoGenerate = true)
    val id : Int?=null

): Parcelable
