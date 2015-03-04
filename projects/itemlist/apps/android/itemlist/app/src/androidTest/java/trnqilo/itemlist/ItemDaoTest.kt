package trnqilo.itemlist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*

@RunWith(AndroidJUnit4::class)
class ItemDaoTest {

  @get:Rule
  val instantTaskExecutorRule = InstantTaskExecutorRule()

  private lateinit var database: AppDatabase
  private lateinit var itemDao: ItemDao

  @Before
  fun setup() {
    database = Room.inMemoryDatabaseBuilder(
      ApplicationProvider.getApplicationContext(),
      AppDatabase::class.java
    ).allowMainThreadQueries().build()
    itemDao = database.itemDao()
  }

  @After
  fun teardown() {
    database.close()
  }

  @Test
  fun insertAndGetItem() = runBlocking {
    val item = Item(title = "Test Item")
    itemDao.insertItem(item)

    val allItems = itemDao.getAllItems().first()
    assertEquals(1, allItems.size)
    assertEquals("Test Item", allItems[0].title)
  }

  @Test
  fun updateItem() = runBlocking {
    val item = Item(title = "Original Title")
    itemDao.insertItem(item)

    var allItems = itemDao.getAllItems().first()
    val originalItem = allItems[0]

    val updatedItem = originalItem.copy(title = "Updated Title")
    itemDao.updateItem(updatedItem)

    allItems = itemDao.getAllItems().first()
    assertEquals(1, allItems.size)
    assertEquals("Updated Title", allItems[0].title)
  }

  @Test
  fun deleteItem() = runBlocking {
    val item = Item(title = "Item to Delete")
    itemDao.insertItem(item)

    var allItems = itemDao.getAllItems().first()
    assertEquals(1, allItems.size)

    itemDao.deleteItem(allItems[0])

    allItems = itemDao.getAllItems().first()
    assertTrue(allItems.isEmpty())
  }

  @Test
  fun getItemById() = runBlocking {
    val item1 = Item(title = "Item 1")
    val item2 = Item(title = "Item 2")
    itemDao.insertItem(item1)
    itemDao.insertItem(item2)

    val allItems = itemDao.getAllItems().first()
    val item1Id = allItems[0].id
    val item2Id = allItems[1].id

    val fetchedItem1 = itemDao.getItemById(item1Id)
    val fetchedItem2 = itemDao.getItemById(item2Id)

    assertNotNull(fetchedItem1)
    assertNotNull(fetchedItem2)
    assertEquals("Item 1", fetchedItem1?.title)
    assertEquals("Item 2", fetchedItem2?.title)
  }

  @Test
  fun getAllItems() = runBlocking {
    val items = listOf(
      Item(title = "Item 1"),
      Item(title = "Item 2"),
      Item(title = "Item 3")
    )

    items.forEach { itemDao.insertItem(it) }

    val allItems = itemDao.getAllItems().first()
    assertEquals(3, allItems.size)
    assertTrue(allItems.any { it.title == "Item 1" })
    assertTrue(allItems.any { it.title == "Item 2" })
    assertTrue(allItems.any { it.title == "Item 3" })
  }
}