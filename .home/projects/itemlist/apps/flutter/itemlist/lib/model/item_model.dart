import 'package:equatable/equatable.dart';
import 'package:item_list/api/item_data.dart';

class Item extends Equatable {
  final String id;
  final String text;
  final bool pending;

  Item({this.id = "", this.text = "", this.pending = false});

  factory Item.fromItemData(ItemData data) => Item(
        id: data.id,
        text: data.text,
      );

  @override
  List<Object> get props => [
        id,
        text,
        pending,
      ];
}
