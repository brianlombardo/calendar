package trnqilo.itemlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ItemViewModel(private val itemDao: ItemDao) : ViewModel() {
  val allItems: Flow<List<Item>> = itemDao.getAllItems()

  private val _currentItem = MutableStateFlow<Item?>(null)
  val currentItem: StateFlow<Item?> = _currentItem

  fun addItem(title: String) {
    viewModelScope.launch(IO) {
      itemDao.insertItem(Item(title = title))
    }
  }

  fun updateItem(item: Item) {
    viewModelScope.launch(IO) {
      itemDao.updateItem(item)
      _currentItem.value = item
    }
  }

  fun deleteItem(item: Item) {
    viewModelScope.launch(IO) {
      itemDao.deleteItem(item)
    }
  }

  fun loadItem(id: Int) {
    viewModelScope.launch(IO) {
      _currentItem.value = itemDao.getItemById(id)
    }
  }

  @Suppress("UNCHECKED_CAST")
  class Factory(private val itemDao: ItemDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = ItemViewModel(itemDao) as T
  }
}
