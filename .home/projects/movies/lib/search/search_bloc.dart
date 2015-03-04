import 'package:flutter_bloc/flutter_bloc.dart';

import '../omdb/omdb_results.dart';
import '../omdb/omdb_service.dart';
import 'events.dart';
import 'states.dart';

abstract class SearchBloc extends Bloc<MovieEvent, MovieState> {
  SearchBloc({MovieState? initialState})
      : super(initialState ?? LoadedState(const []));
}

class ConnectedSearchBloc extends SearchBloc {
  final OmdbService _service;

  ConnectedSearchBloc({OmdbService? service})
      : _service = service ?? OmdbService(),
        super() {
    on<SearchEvent>((event, emit) async {
      final query = event.query.trim();

      if (query.isEmpty) {
        return;
      }

      emit(LoadingState());

      final searchResult = await _service.search(query);
      if (searchResult is SearchResult) {
        emit(LoadedState(searchResult.resultList));
      } else if (searchResult is ErrorResult) {
        emit(ErrorState(searchResult.message));
      }
    });

    on<SelectEvent>((event, emit) async {
      emit(LoadingState());
      final detailsResult = await _service.getDetails(event.title);
      if (detailsResult is DetailsResult) {
        emit(DetailsLoadedState(detailsResult));
      } else if (detailsResult is ErrorResult) {
        emit(ErrorState(detailsResult.message));
      }
    });
  }
}
