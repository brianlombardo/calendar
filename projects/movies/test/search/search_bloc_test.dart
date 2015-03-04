import 'package:bloc_test/bloc_test.dart';
import 'package:flutter_movies/omdb/movie_models.dart';
import 'package:flutter_movies/omdb/omdb_results.dart';
import 'package:flutter_movies/omdb/omdb_service.dart';
import 'package:flutter_movies/search/events.dart';
import 'package:flutter_movies/search/search_bloc.dart';
import 'package:flutter_movies/search/states.dart';
import 'package:flutter_test/flutter_test.dart';

void main() {
  const results = [Movie('title', 'year')];
  const errorMessage = 'bad things happened';

  group('Search Bloc', () {
    test('is initially loaded with empty list', () {
      expect(ConnectedSearchBloc().state, LoadedState(const []));
    });

    blocTest('does not emit the initial state',
        build: () => ConnectedSearchBloc(), expect: () => []);

    blocTest(
      'does not emit when search terms are blank',
      build: () => ConnectedSearchBloc(service: null),
      act: (ConnectedSearchBloc bloc) async => bloc.add(SearchEvent(' ')),
      expect: () => [],
    );

    blocTest(
      'emits loaded state with movie list after successful search',
      build: () => ConnectedSearchBloc(
          service: FakeService(returns: SearchResult(resultList: results))),
      act: (ConnectedSearchBloc bloc) {
        return bloc.add(SearchEvent(FakeService.expectedQuery));
      },
      expect: () => [LoadingState(), LoadedState(results)],
    );

    blocTest(
      'calls the service with trimmed search terms',
      build: () => ConnectedSearchBloc(
          service: FakeService(returns: SearchResult(resultList: results))),
      act: (ConnectedSearchBloc bloc) async =>
          bloc.add(SearchEvent('   ${FakeService.expectedQuery}    ')),
      expect: () => [LoadingState(), LoadedState(results)],
    );

    blocTest(
      'emits error state after unsuccessful search',
      build: () => ConnectedSearchBloc(
          service: FakeService(returns: ErrorResult(errorMessage))),
      act: (ConnectedSearchBloc bloc) async =>
          bloc.add(SearchEvent(FakeService.expectedQuery)),
      expect: () => [LoadingState(), ErrorState(errorMessage)],
    );

    blocTest(
      'emits error state when getting details is unsuccessful',
      build: () => ConnectedSearchBloc(
          service: FakeService(returns: ErrorResult(errorMessage))),
      act: (ConnectedSearchBloc bloc) async => bloc.add(SelectEvent('title')),
      expect: () => [LoadingState(), ErrorState(errorMessage)],
    );

    final detailsResult = DetailsResult(const MovieDetails('t', 'y', 'i', 'p'));
    blocTest(
      'emits details loaded state when getting details is successful',
      build: () =>
          ConnectedSearchBloc(service: FakeService(returns: detailsResult)),
      act: (ConnectedSearchBloc bloc) async => bloc.add(SelectEvent('title')),
      expect: () => [LoadingState(), DetailsLoadedState(detailsResult)],
    );
  });
}

class FakeService implements OmdbService {
  static const expectedQuery = 'query';

  final OmdbResult returns;

  FakeService({required this.returns});

  @override
  Future<OmdbResult> search(String query) => _getResult(query);

  @override
  Future<OmdbResult> getDetails(String query) => _getResult(query);

  Future<OmdbResult> _getResult(String query) =>
      returns is! SearchResult || query == expectedQuery
          ? Future.value(returns)
          : throw FakeServiceQueryError();
}

class FakeServiceQueryError extends Error {}
