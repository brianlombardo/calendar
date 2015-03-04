import 'package:equatable/equatable.dart';

class Movie extends Equatable {
  final String title;
  final String year;

  const Movie(this.title, this.year);

  factory Movie.fromJson(Map<String, dynamic> json) => Movie(
        json['Title'],
        json['Year'],
      );

  @override
  List<Object?> get props => [title, year];
}

class MovieDetails extends Movie {
  final String plot;
  final String imdbUrl;

  const MovieDetails(super.title, super.year, this.imdbUrl, this.plot);

  factory MovieDetails.fromJson(Map<String, dynamic> json) => MovieDetails(
        json['Title'],
        json['Year'],
        'https://www.imdb.com/title/${json['imdbID']}',
        json['Plot'],
      );

  @override
  List<Object?> get props => [title, year, imdbUrl, plot];
}
