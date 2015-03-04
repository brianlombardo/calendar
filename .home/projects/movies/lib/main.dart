import 'package:flutter/material.dart';
import 'package:flutter_movies/search/search_screen.dart';
import 'package:flutter_movies/theme.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) => MaterialApp(
        title: 'Movie app',
        theme: ThemeData(
            primarySwatch: primarySwatch,
            scaffoldBackgroundColor: primarySwatch.shade900,
            textSelectionTheme:
                const TextSelectionThemeData(selectionColor: Colors.grey)),
        home: MovieSearchScreen(),
        debugShowCheckedModeBanner: false,
      );
}
