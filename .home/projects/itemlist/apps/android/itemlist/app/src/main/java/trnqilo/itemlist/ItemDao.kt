package trnqilo.itemlist

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {
  @Query("SELECT * FROM items")
  fun getAllItems(): Flow<List<Item>>

  @Insert(onConflict = REPLACE)
  suspend fun insertItem(item: Item)

  @Update
  suspend fun updateItem(item: Item)

  @Delete
  suspend fun deleteItem(item: Item)

  @Query("SELECT * FROM items WHERE id = :id")
  suspend fun getItemById(id: Int): Item?
}
