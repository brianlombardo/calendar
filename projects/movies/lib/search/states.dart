import 'package:equatable/equatable.dart';

import '../omdb/movie_models.dart';
import '../omdb/omdb_results.dart';

abstract class MovieState extends Equatable {}

class LoadedState extends MovieState {
  final List<Movie> results;

  LoadedState(this.results);

  @override
  List<Object?> get props => [results];
}

class DetailsLoadedState extends MovieState {
  final DetailsResult details;

  DetailsLoadedState(this.details);

  @override
  List<Object?> get props => [details];
}

class LoadingState extends MovieState {
  @override
  List<Object?> get props => [];
}

class ErrorState extends MovieState {
  final String message;

  ErrorState(this.message);

  @override
  List<Object?> get props => [message];
}
