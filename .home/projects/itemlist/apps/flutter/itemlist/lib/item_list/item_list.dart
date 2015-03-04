import 'package:flutter/material.dart';
import 'package:item_list/item_details/item_details_screen.dart';
import 'package:item_list/item_list/items_bloc.dart';
import 'package:item_list/model/item_model.dart';

import 'events.dart';

class ItemList extends StatelessWidget {
  final ItemsBloc _bloc;
  final List<Item> _items;

  ItemList({ItemsBloc bloc, List<Item> items})
      : this._bloc = bloc,
        this._items = items ?? [];

  @override
  Widget build(BuildContext context) => ListView.builder(
        itemBuilder: (context, index) {
          final Item item = _items[index];
          return Dismissible(
            onDismissed: (direction) => {_bloc.add(DeleteItem(item.id))},
            key: Key(item.id),
            child: ListTile(
              title: Hero(
                  tag: item.id,
                  child: Material(
                    child: Text(item.text),
                    color: Colors.transparent,
                  )),
              trailing: item.pending ? CircularProgressIndicator() : null,
              onTap: () {
                Navigator.push(
                  context,
                  MaterialPageRoute(
                      builder: (context) => ItemDetailsScreen(item)),
                );
              },
            ),
          );
        },
        itemCount: _items.length,
      );
}
