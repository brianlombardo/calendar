import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_movies/search/search_bloc.dart';

import '../omdb/movie_models.dart';
import 'events.dart';

class SearchResultTile extends StatelessWidget {
  final Movie _movie;

  const SearchResultTile(this._movie, {super.key});

  @override
  Widget build(BuildContext context) => Container(
        alignment: Alignment.center,
        decoration: BoxDecoration(
          color: Colors.white,
          borderRadius: BorderRadius.circular(16),
        ),
        child: InkWell(
            child: Column(
              children: [
                Expanded(child: Container(color: Colors.black12)),
                Padding(
                    padding: const EdgeInsets.all(8.0),
                    child: Text(
                      _movie.title,
                      overflow: TextOverflow.ellipsis,
                      style: const TextStyle(fontSize: 18),
                    )),
                Padding(
                    padding: const EdgeInsets.only(bottom: 8.0),
                    child: Text(
                      _movie.year,
                      overflow: TextOverflow.ellipsis,
                      style: const TextStyle(fontSize: 12),
                    )),
              ],
            ),
            onTap: () {
              final bloc = BlocProvider.of<SearchBloc>(context);
              bloc.add(SelectEvent(_movie.title));
            }),
      );
}
