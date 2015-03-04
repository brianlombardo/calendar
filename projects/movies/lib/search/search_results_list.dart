import 'package:flutter/material.dart';
import 'package:flutter_movies/search/search_result_tile.dart';

import '../omdb/movie_models.dart';

class SearchResultsList extends StatelessWidget {
  final List<Movie> _results;

  SearchResultsList({super.key, List<Movie>? results})
      : _results = results ?? [];

  @override
  Widget build(BuildContext context) => SingleChildScrollView(
        child: GridView.builder(
            physics: const NeverScrollableScrollPhysics(),
            shrinkWrap: true,
            gridDelegate: const SliverGridDelegateWithMaxCrossAxisExtent(
              maxCrossAxisExtent: 300,
              childAspectRatio: 1,
              crossAxisSpacing: 16,
              mainAxisSpacing: 16,
            ),
            itemCount: _results.length,
            itemBuilder: (_, index) => SearchResultTile(_results[index])),
      );
}
