package com.ctis487_fp.finalproject.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "medicine_table")
data class MedicineItem(
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    val id: Int = 0,

    @SerializedName("type")
    val type: Int = 1,

    @SerializedName("title")
    val title: String = "",

    @SerializedName("description")
    val description: String = "",

    @SerializedName("stock")
    val stock: Int = 0,

    @SerializedName("imageUrl")
    val imageUrl: String = "",

    var isFavorite: Boolean = false
) {
    override fun toString(): String {
        return "Medicine(id=$id, type=$type, title='$title', description='$description', stock=$stock, imageUrl='$imageUrl', isFavorite=$isFavorite)"
    }
}
