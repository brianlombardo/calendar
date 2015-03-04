import 'package:equatable/equatable.dart';
import 'package:item_list/model/item_model.dart';

abstract class ItemListState extends Equatable {
  @override
  List<Object> get props => [];
}

class Error extends ItemListState {
  final String message;

  Error(this.message);

  @override
  List<Object> get props => [message];
}

class Loaded extends ItemListState {
  final List<Item> items;

  Loaded(this.items);

  @override
  List<Object> get props => [items];
}

class Initial extends Loaded {
  Initial() : super([]);
}
