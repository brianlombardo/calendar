import 'package:flutter/material.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:item_list/item_list/events.dart';
import 'package:item_list/item_list/item_input.dart';

import '../fake_items_bloc.dart';

void main() {
  testWidgets('clicking add button will send field text to the bloc',
      (WidgetTester tester) async {
    final FakeItemsBloc bloc = FakeItemsBloc();
    await tester.pumpWidget(
      MaterialApp(
        home: Scaffold(
          body: ItemInput(
            bloc: bloc,
          ),
        ),
      ),
    );

    final testText = 'Item text';

    await tester.enterText(find.byType(TextField), testText);
    await tester.tap(find.byIcon(Icons.add));
    await tester.pump();

    expect(bloc.events, equals([AddItem(testText)]));
    bloc.close();
  });
}
