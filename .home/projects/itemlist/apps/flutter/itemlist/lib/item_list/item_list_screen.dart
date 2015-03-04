import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:item_list/item_list/items_bloc.dart';
import 'package:item_list/item_list/states.dart';

import 'events.dart';
import 'item_input.dart';
import 'item_list.dart';

class ItemListScreen extends StatelessWidget {
  final _itemsBloc;

  ItemListScreen({ItemsBloc itemsBloc})
      : _itemsBloc = itemsBloc ?? ConnectedItemsBloc();

  @override
  Widget build(BuildContext context) {
    _itemsBloc.add(RefreshItems());
    return BlocProvider<ItemsBloc>.value(
      value: _itemsBloc,
      child: Scaffold(
        appBar: AppBar(),
        body: _ItemListBody(itemsBloc: _itemsBloc),
      ),
    );
  }
}

class _ItemListBody extends StatelessWidget {
  final ItemsBloc _itemsBloc;

  const _ItemListBody({ItemsBloc itemsBloc}) : _itemsBloc = itemsBloc;

  @override
  Widget build(BuildContext context) {
    return BlocConsumer<ItemsBloc, ItemListState>(
      buildWhen: (_, current) => current is Loaded,
      listenWhen: (_, current) => current is Error,
      builder: (context, state) => Column(
        children: <Widget>[
          Padding(
            padding: const EdgeInsets.all(8.0),
            child: ItemInput(bloc: _itemsBloc),
          ),
          Expanded(
            flex: 1,
            child: Container(
                child: ItemList(
              bloc: _itemsBloc,
              items: (state as Loaded).items,
            )),
          ),
        ],
      ),
      listener: (context, state) {
        if (state is Error) {
          Scaffold.of(context).showSnackBar(SnackBar(
            content: Text(state.message),
          ));
        }
      },
    );
  }
}
