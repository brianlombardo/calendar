package trnqilo.itemlist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room.databaseBuilder
import trnqilo.itemlist.ItemViewModel.Factory

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      MaterialTheme {
        ItemListApp(
          ViewModelProvider(
            this, Factory(
              databaseBuilder(applicationContext, AppDatabase::class.java, "item-database")
                .build()
                .itemDao()
            )
          )[ItemViewModel::class.java]
        )
      }
    }
  }
}
