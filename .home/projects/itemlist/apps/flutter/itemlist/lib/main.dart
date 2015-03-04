import 'package:flutter/material.dart';

import 'item_list/item_list_screen.dart';

void main() => runApp(MyApp());

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) =>
      MaterialApp(
        title: 'Item List',
        theme: ThemeData(
          primarySwatch: Colors.blue,
        ),
        home: ItemListScreen(),
      );
}
