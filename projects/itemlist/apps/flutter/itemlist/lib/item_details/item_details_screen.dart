import 'package:flutter/material.dart';

import '../model/item_model.dart';

class ItemDetailsScreen extends StatelessWidget {
  final Item _item;

  ItemDetailsScreen(this._item);

  @override
  Widget build(BuildContext context) => Scaffold(
        floatingActionButton: FloatingActionButton(
          onPressed: () {},
        ),
        appBar: AppBar(),
        body: Column(
          children: <Widget>[
            Padding(
              padding: const EdgeInsets.all(16.0),
              child: Hero(
                tag: _item.id,
                child: Material(
                  child: Text(_item.text),
                  color: Colors.transparent,
                ),
              ),
            ),
          ],
        ),
      );
}
