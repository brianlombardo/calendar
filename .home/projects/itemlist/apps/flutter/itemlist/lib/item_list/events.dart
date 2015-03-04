import 'package:equatable/equatable.dart';

abstract class ItemListEvent extends Equatable {
  @override
  List<Object> get props => [];
}

class AddItem extends ItemListEvent {
  final String itemText;

  AddItem(this.itemText);

  @override
  List<Object> get props => [itemText];
}

class DeleteItem extends ItemListEvent {
  final String itemId;

  DeleteItem(this.itemId);

  @override
  List<Object> get props => [itemId];
}

class RefreshItems extends ItemListEvent {}
