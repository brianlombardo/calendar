package trnqilo.itemlist

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavType.Companion.IntType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun ItemListApp(viewModel: ItemViewModel) {
  val navController = rememberNavController()
  val items by viewModel.allItems.collectAsState(initial = emptyList())

  NavHost(navController = navController, startDestination = "itemList") {
    composable("itemList") {
      ItemsList(
        items = items,
        onItemClick = { itemId ->
          navController.navigate("itemDetail/$itemId")
        },
        onAddItem = {
          navController.navigate("addItem")
        }
      )
    }
    composable(
      "itemDetail/{itemId}",
      arguments = listOf(navArgument("itemId") { type = IntType })
    ) { backStackEntry ->
      ItemDetails(
        itemId = backStackEntry.arguments?.getInt("itemId") ?: return@composable,
        viewModel = viewModel,
        onBack = { navController.popBackStack() }
      )
    }
    composable("addItem") {
      ItemEditor(
        onItemSaved = { title ->
          viewModel.addItem(title)
          navController.popBackStack()
        },
        onBackClick = { navController.popBackStack() }
      )
    }
  }
}
