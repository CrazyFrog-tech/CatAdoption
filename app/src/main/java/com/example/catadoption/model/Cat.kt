package com.example.catadoption.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "catTable")
data class Cat(
    @ColumnInfo(name = "Name")
    @SerializedName("Name") var Name: String,
    @ColumnInfo(name = "Image_url")
    @SerializedName("Image_url") var Image_url: String,
    @ColumnInfo(name = "Description")
    @SerializedName("Description") var Description: String,
    @ColumnInfo(name = "Cattributes")
    @SerializedName("Cattributes") var Cattributes: String,
    @ColumnInfo(name = "Background_url")
    @SerializedName("Background_url") var Background_url: String,
    @ColumnInfo(name = "isFavourite") var isFav: Int? = 0,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long? = null

) : Parcelable {
    constructor() : this("", "", "", "", "")
}