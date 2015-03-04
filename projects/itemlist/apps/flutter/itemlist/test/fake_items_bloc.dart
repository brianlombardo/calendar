import 'package:item_list/item_list/events.dart';
import 'package:item_list/item_list/items_bloc.dart';
import 'package:item_list/item_list/states.dart';

class FakeItemsBloc extends ItemsBloc {
  List<ItemListState> states = [];
  List<ItemListEvent> events = [];

  @override
  Stream<ItemListState> mapEventToState(ItemListEvent event) async* {
    events = [...events, event];
    for (ItemListState state in states) {
      yield state;
    }
    states = [];
  }
}
