import 'package:equatable/equatable.dart';
import 'movie_models.dart';

abstract class OmdbResult extends Equatable {}

class SearchResult extends OmdbResult {
  final List<Movie> resultList;

  SearchResult({required this.resultList});

  factory SearchResult.fromJson(Map<String, dynamic> json) => SearchResult(
        resultList: List<Movie>.from(
          json['Search'].map((movieJson) => Movie.fromJson(movieJson)),
        ),
      );

  @override
  List<Object?> get props => [resultList];
}

class ErrorResult extends OmdbResult {
  final String message;

  ErrorResult(this.message);

  @override
  List<Object?> get props => [message];
}

class DetailsResult extends OmdbResult {
  final MovieDetails details;

  DetailsResult(this.details);

  factory DetailsResult.fromJson(Map<String, dynamic> json) =>
      DetailsResult(MovieDetails.fromJson(json));

  @override
  List<Object?> get props => [details];
}
