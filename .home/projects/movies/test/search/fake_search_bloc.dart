
import 'package:flutter_movies/search/events.dart';
import 'package:flutter_movies/search/search_bloc.dart';
import 'package:flutter_movies/search/states.dart';

class FakeSearchBloc extends SearchBloc {
  List<MovieState> states = [];
  List<MovieEvent> events = [];

  FakeSearchBloc() {
    on<MovieEvent>((event, emit) {
      events = [...events, event];
      for (MovieState state in states) {
        emit(state);
      }
      states = [];
    });
  }
}
