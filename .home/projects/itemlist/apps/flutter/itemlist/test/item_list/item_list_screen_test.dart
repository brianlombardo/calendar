import 'package:flutter/material.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:item_list/item_list/events.dart';
import 'package:item_list/item_list/item_input.dart';
import 'package:item_list/item_list/item_list.dart';
import 'package:item_list/item_list/item_list_screen.dart';
import 'package:item_list/item_list/states.dart';
import 'package:item_list/model/item_model.dart';

import '../fake_items_bloc.dart';

void main() {
  testWidgets('initially shows empty list', (WidgetTester tester) async {
    final fakeItemsBloc = FakeItemsBloc();
    await tester.pumpWidget(
      MaterialApp(
        home: Scaffold(
          body: ItemListScreen(itemsBloc: fakeItemsBloc),
        ),
      ),
    );
    expect(find.byType(ItemInput), findsOneWidget);
    expect(find.byType(ItemList), findsOneWidget);
    expect(find.byType(ListTile), findsNothing);
    fakeItemsBloc.close();
  });

  testWidgets('shows data when loaded',
      (WidgetTester tester) async {
    final fakeItemsBloc = FakeItemsBloc();
    final itemText = 'loaded item';
    fakeItemsBloc.states = [
      Loaded([Item(text: itemText)])
    ];

    await tester.pumpWidget(
      MaterialApp(
        home: Scaffold(
          body: ItemListScreen(itemsBloc: fakeItemsBloc),
        ),
      ),
    );

    expect(find.byType(ItemInput), findsOneWidget);
    expect(find.byType(ItemList), findsOneWidget);
    expect(find.byType(ListTile), findsNothing);

    await tester.pump(Duration.zero);

    expect(find.byType(ItemInput), findsOneWidget);
    expect(find.byType(ItemList), findsOneWidget);
    expect(find.byType(ListTile), findsOneWidget);

    expect(fakeItemsBloc.events, equals([RefreshItems()]));
    final actualText = (find.byType(Text).evaluate().first.widget as Text).data;
    expect(actualText, equals(itemText));

    fakeItemsBloc.close();
  });

  testWidgets('shows message when an error occurs',
      (WidgetTester tester) async {
    final message = 'error message';
    final fakeItemsBloc = FakeItemsBloc();
    fakeItemsBloc.states = [Error(message)];
    await tester.pumpWidget(
      MaterialApp(
        home: Scaffold(
          body: ItemListScreen(itemsBloc: fakeItemsBloc),
        ),
      ),
    );
    await tester.pump(Duration.zero);

    expect(find.byType(SnackBar), findsOneWidget);
    expect(find.text(message), findsOneWidget);
    expect(find.byType(ItemInput), findsOneWidget);
    expect(find.byType(ItemList), findsOneWidget);
    fakeItemsBloc.close();
  });
}
