import 'package:flutter/material.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:item_list/item_details/item_details_screen.dart';
import 'package:item_list/item_list/events.dart';
import 'package:item_list/item_list/item_list.dart';
import 'package:item_list/model/item_model.dart';

import '../fake_items_bloc.dart';
import '../test_extensions.dart';

void main() {
  testWidgets('list is initially empty', (WidgetTester tester) async {
    await tester.pumpWidget(
      MaterialApp(
        home: Scaffold(
          body: ItemList(),
        ),
      ),
    );

    expect(find.byType(ListView), findsOneWidget);

    ListView list = find.byType(ListView).evaluate().first.widget;

    expect(list.semanticChildCount, equals(0));

    expect(find.byType(ListTile), findsNothing);
  });

  testWidgets('when items are provided, then the list will display them',
      (WidgetTester tester) async {
    final testData = [
      Item(text: 'Test'),
      Item(text: 'Test 2'),
      Item(text: 'Test 3')
    ];

    await tester.pumpWidget(
      MaterialApp(
        home: Scaffold(
          body: ItemList(
            items: testData,
          ),
        ),
      ),
    );

    Finder listFinder = find.byType(ListView);
    Finder tileFinder = find.byType(ListTile);
    Finder textFinder = find.byType(Text);

    expect(listFinder.atIndex<ListView>(0).semanticChildCount, equals(3));
    expect(tileFinder, findsNWidgets(3));
    expect(textFinder, findsNWidgets(3));
    expect(find.byType(CircularProgressIndicator), findsNothing);
    expect(textFinder.atIndex<Text>(0).data, testData[0].text);
    expect(textFinder.atIndex<Text>(1).data, testData[1].text);
    expect(textFinder.atIndex<Text>(2).data, testData[2].text);
  });

  testWidgets('when item is selected, then the details screen is shown',
      (WidgetTester tester) async {
    final testData = [Item(text: 'Test')];

    await tester.pumpWidget(
      MaterialApp(
        home: Scaffold(
          body: ItemList(
            items: testData,
          ),
        ),
      ),
    );

    Finder tileFinder = find.byType(ListTile);
    expect(tileFinder, findsOneWidget);

    await tester.tap(tileFinder);
    await tester.pumpAndSettle();

    expect(find.byType(ItemDetailsScreen), findsNWidgets(1));
    var textFinder = find.byType(Text);
    expect(textFinder, findsOneWidget);
    expect(textFinder.atIndex<Text>(0).data, testData[0].text);
  });

  testWidgets(
      'when item is swiped away, then the item should be removed from list',
      (WidgetTester tester) async {
    final FakeItemsBloc bloc = FakeItemsBloc();

    final testData = [Item(id: 'ID', text: 'Test')];

    await tester.pumpWidget(
      MaterialApp(
        home: Scaffold(
          body: ItemList(
            bloc: bloc,
            items: testData,
          ),
        ),
      ),
    );

    Finder dismissibleFinder = find.byType(Dismissible);
    expect(dismissibleFinder, findsOneWidget);

    await tester.drag(dismissibleFinder, Offset(500.0, 0.0));
    await tester.pumpAndSettle();

    expect(dismissibleFinder, findsNothing);
    expect(bloc.events, equals([DeleteItem('ID')]));
    bloc.close();
  });

  testWidgets('when a pending item is provided, then an indicator is shown',
      (WidgetTester tester) async {
    final testData = [
      Item(text: 'Test'),
      Item(text: 'Test 2', pending: true),
      Item(text: 'Test 3')
    ];

    await tester.pumpWidget(
      MaterialApp(
        home: Scaffold(
          body: ItemList(
            items: testData,
          ),
        ),
      ),
    );

    Finder tileFinder = find.byType(ListTile);
    Finder indicatorFinder = find.byType(CircularProgressIndicator);

    expect(tileFinder, findsNWidgets(3));
    expect(indicatorFinder, findsNWidgets(1));
    expect(tileFinder.atIndex<ListTile>(1).trailing,
        isInstanceOf<CircularProgressIndicator>());
  });
}
