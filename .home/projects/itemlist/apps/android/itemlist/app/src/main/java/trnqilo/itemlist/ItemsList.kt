package trnqilo.itemlist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons.Default
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ItemsList(
  items: List<Item>,
  onItemClick: (Int) -> Unit,
  onAddItem: () -> Unit
) {
  Scaffold(
    floatingActionButton = {
      FloatingActionButton(onClick = onAddItem) {
        Icon(Default.Add, contentDescription = "Add item")
      }
    }
  ) { paddingValues ->
    LazyColumn(
      modifier = Modifier.padding(paddingValues)
    ) {
      items(items) { item ->
        ListItem(
          headlineContent = { Text(item.title) },
          modifier = Modifier.clickable { onItemClick(item.id) }
        )
        HorizontalDivider()
      }
    }
  }
}
