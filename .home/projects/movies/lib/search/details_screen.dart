import 'package:flutter/material.dart';

import '../omdb/movie_models.dart';
import 'imdb_link.dart';

class MovieDetailsScreen extends StatelessWidget {
  final MovieDetails _details;

  const MovieDetailsScreen(this._details, {super.key});

  @override
  Widget build(BuildContext context) => Scaffold(
        appBar: AppBar(
          title: Text(_details.title),
        ),
        body: SingleChildScrollView(
          child: Center(
            heightFactor: 1,
            child: Padding(
              padding: const EdgeInsets.all(16.0),
              child: Container(
                decoration: BoxDecoration(
                  color: Colors.white,
                  borderRadius: BorderRadius.circular(16),
                ),
                child: Column(
                  mainAxisSize: MainAxisSize.max,
                  children: [
                    Padding(
                      padding: const EdgeInsets.all(16.0),
                      child: Text(_details.plot),
                    ),
                    Padding(
                      padding: const EdgeInsets.only(bottom: 32.0, top: 16),
                      child: ImdbLink(_details.imdbUrl),
                    )
                  ],
                ),
              ),
            ),
          ),
        ),
      );
}
