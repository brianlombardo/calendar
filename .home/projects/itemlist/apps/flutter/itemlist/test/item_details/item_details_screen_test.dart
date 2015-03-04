import 'package:flutter/material.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:item_list/item_details/item_details_screen.dart';
import 'package:item_list/model/item_model.dart';

void main() {
  final itemText = 'loaded item';
  final item = Item(text: itemText);

  testWidgets('shows current item', (WidgetTester tester) async {
    await tester.pumpWidget(
      MaterialApp(
        home: Scaffold(
          body: ItemDetailsScreen(item),
        ),
      ),
    );

    final actualText = (find.byType(Text).evaluate().first.widget as Text).data;
    expect(actualText, equals(itemText));
  });

  // testWidgets('clicking fab enters edit mode', (WidgetTester tester) async {
  //   await tester.pumpWidget(
  //     MaterialApp(
  //       home: Scaffold(
  //         body: ItemDetailsScreen(item),
  //       ),
  //     ),
  //   );
  //
  //   final textFinder = find.byType(Text);
  //   final textFieldFinder = find.byType(TextField);
  //
  //   expect(textFinder, findsOneWidget);
  //   expect(textFieldFinder, findsNothing);
  //
  //   await tester.tap(find.byType(FloatingActionButton));
  //   await tester.pump();
  //
  //   expect(textFinder, findsNothing);
  //   expect(textFieldFinder, findsOneWidget);
  // });
}
