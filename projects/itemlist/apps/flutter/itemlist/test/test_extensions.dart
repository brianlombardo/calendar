import 'package:flutter/widgets.dart';
import 'package:flutter_test/flutter_test.dart';

extension WidgetFinder on Finder {
  W atIndex<W extends Widget>(int index) {
    return this.evaluate().elementAt(index).widget;
  }
}
