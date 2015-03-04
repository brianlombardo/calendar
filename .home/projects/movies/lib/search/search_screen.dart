import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_movies/search/search_input.dart';
import 'package:flutter_movies/search/search_results_list.dart';
import 'package:flutter_movies/search/states.dart';

import 'details_screen.dart';
import 'search_bloc.dart';

class MovieSearchScreen extends StatelessWidget {
  final SearchBloc _bloc;

  MovieSearchScreen({super.key, SearchBloc? bloc})
      : _bloc = bloc ?? ConnectedSearchBloc();

  @override
  Widget build(BuildContext context) => Scaffold(
        appBar: AppBar(
          title: const Text('Movie app'),
        ),
        body: Center(
          child: Padding(
            padding: const EdgeInsets.all(16.0),
            child: Column(
              mainAxisAlignment: MainAxisAlignment.center,
              children: <Widget>[
                Padding(
                    padding: const EdgeInsets.only(bottom: 16.0),
                    child: SearchInput(_bloc)),
                Expanded(
                  child: BlocProvider(
                    create: (_) => _bloc,
                    child: BlocListener<SearchBloc, MovieState>(
                      listener: (context, state) {
                        if (state is ErrorState) {
                          ScaffoldMessenger.of(context).showSnackBar(
                            SnackBar(
                              content: Text(state.message),
                            ),
                          );
                        } else if (state is DetailsLoadedState) {
                          Navigator.push(
                            context,
                            MaterialPageRoute(
                                builder: (context) =>
                                    MovieDetailsScreen(state.details.details)),
                          );
                        }
                      },
                      child: BlocBuilder<SearchBloc, MovieState>(
                        // TODO: retain results during error and loading states
                        // buildWhen: (MovieState previous, MovieState current) =>
                        //     current is LoadedState && current != previous,
                        builder: (_, state) {
                          if (state is LoadingState) {
                            return const Center(
                                child: CircularProgressIndicator());
                          } else if (state is LoadedState) {
                            return SearchResultsList(results: state.results);
                          } else {
                            return Container();
                          }
                        },
                      ),
                    ),
                  ),
                ),
              ],
            ),
          ),
        ),
      );
}
