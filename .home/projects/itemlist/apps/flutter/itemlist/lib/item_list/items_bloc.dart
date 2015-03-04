import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:item_list/api/item_data.dart';
import 'package:item_list/item_list/events.dart';
import 'package:item_list/item_list/items_repository.dart';
import 'package:item_list/item_list/states.dart';
import 'package:item_list/model/item_model.dart';

abstract class ItemsBloc extends Bloc<ItemListEvent, ItemListState> {
  @override
  ItemListState get initialState => Initial();
}

class ConnectedItemsBloc extends ItemsBloc {
  List<Item> _items = [];
  final ItemsRepository _repo;

  ConnectedItemsBloc({ItemsRepository repo})
      : _repo = repo ?? ItemsRepository();

  @override
  Stream<ItemListState> mapEventToState(ItemListEvent event) async* {
    switch (event.runtimeType) {
      case AddItem:
        {
          final text = (event as AddItem).itemText.trim();
          if (text.isEmpty) {
            yield Error('Item text cannot be blank');
            return;
          }
          yield (Loaded([Item(text: text, pending: true), ..._items]));
          await _repo.createItem(text);
          break;
        }
      case DeleteItem:
        {
          final id = (event as DeleteItem).itemId;
          await _repo.deleteItem(id);
          break;
        }
    }
    final itemsData = await _repo.getItems();
    _updateItems(itemsData);
    _sortItems();
    yield Loaded(_items);
  }

  _updateItems(List<ItemData> itemsData) {
    _items = itemsData.map((itemData) => Item.fromItemData(itemData)).toList();
  }

  void _sortItems() {
    _items.sort((item1, item2) => item1.id.compareTo(item2.id));
    _items = _items.reversed.toList();
  }
}
