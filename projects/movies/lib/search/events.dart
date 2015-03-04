import 'package:equatable/equatable.dart';

abstract class MovieEvent extends Equatable {}

class SearchEvent extends MovieEvent {
  final String query;

  SearchEvent(this.query);

  @override
  List<Object?> get props => [query];
}

class SelectEvent extends MovieEvent {
  final String title;

  SelectEvent(this.title);

  @override
  List<Object?> get props => [title];
}
