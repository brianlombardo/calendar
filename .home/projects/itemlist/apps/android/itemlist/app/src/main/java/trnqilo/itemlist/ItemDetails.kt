package trnqilo.itemlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons.AutoMirrored.Filled
import androidx.compose.material.icons.Icons.Default
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemDetails(
  itemId: Int,
  viewModel: ItemViewModel,
  onBack: () -> Unit
) {
  var isEditing by remember { mutableStateOf(false) }
  var showDeleteConfirmation by remember { mutableStateOf(false) }

  val currentItem by viewModel.currentItem.collectAsState()

  LaunchedEffect(itemId) {
    viewModel.loadItem(itemId)
  }

  Scaffold(
    topBar = {
      TopAppBar(
        title = { Text(if (isEditing) "Edit Item" else "Item Details") },
        navigationIcon = {
          IconButton(onClick = onBack) {
            Icon(Filled.ArrowBack, contentDescription = "Back")
          }
        },
        actions = {
          if (!isEditing) {
            IconButton(onClick = { showDeleteConfirmation = true }) {
              Icon(Default.Delete, contentDescription = "Delete")
            }
            TextButton(onClick = { isEditing = true }) {
              Text("Edit")
            }
          }
        }
      )
    }
  ) { paddingValues ->
    Column(
      modifier = Modifier
        .padding(paddingValues)
        .padding(16.dp)
    ) {
      currentItem?.let { item ->
        if (isEditing) {
          var editedTitle by remember(item.title) { mutableStateOf(item.title) }
          OutlinedTextField(
            value = editedTitle,
            onValueChange = { editedTitle = it },
            label = { Text("Item Title") },
            modifier = Modifier.fillMaxWidth()
          )
          Spacer(modifier = Modifier.height(16.dp))
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            TextButton(onClick = { isEditing = false }) {
              Text("Cancel")
            }
            Button(
              onClick = {
                viewModel.updateItem(item.copy(title = editedTitle))
                isEditing = false
              }
            ) {
              Text("Save")
            }
          }
        } else {
          Text(item.title, style = typography.headlineMedium)
        }
      }
    }
  }

  if (showDeleteConfirmation) {
    AlertDialog(
      onDismissRequest = { showDeleteConfirmation = false },
      title = { Text("Delete Item") },
      text = { Text("Are you sure you want to delete this item?") },
      confirmButton = {
        TextButton(
          onClick = {
            currentItem?.let { viewModel.deleteItem(it) }
            showDeleteConfirmation = false
            onBack()
          }
        ) {
          Text("Delete")
        }
      },
      dismissButton = {
        TextButton(onClick = { showDeleteConfirmation = false }) {
          Text("Cancel")
        }
      }
    )
  }
}
