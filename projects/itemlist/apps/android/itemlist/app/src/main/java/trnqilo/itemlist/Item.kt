package trnqilo.itemlist

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items")
data class Item(
  @PrimaryKey(autoGenerate = true) val id: Int = 0,
  @ColumnInfo(name = "title") var title: String
)
