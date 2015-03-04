import 'package:flutter/material.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:flutter_movies/omdb/movie_models.dart';
import 'package:flutter_movies/omdb/omdb_results.dart';
import 'package:flutter_movies/search/details_screen.dart';
import 'package:flutter_movies/search/events.dart';
import 'package:flutter_movies/search/imdb_link.dart';
import 'package:flutter_movies/search/search_input.dart';
import 'package:flutter_movies/search/search_result_tile.dart';
import 'package:flutter_movies/search/search_results_list.dart';
import 'package:flutter_movies/search/search_screen.dart';
import 'package:flutter_movies/search/states.dart';

import 'fake_search_bloc.dart';

void main() {
  group(
    'Search Screen',
    () {
      testWidgets('initially shows search input and empty list',
          (WidgetTester tester) async {
        await tester.pumpWidget(
          MaterialApp(
            home: MovieSearchScreen(bloc: null),
          ),
        );

        expect(find.byType(SearchInput), findsOneWidget);
        expect(find.byType(SearchResultsList), findsOneWidget);
        expect(find.byType(SearchResultTile), findsNothing);
      });

      testWidgets('shows indicator on loading state',
          (WidgetTester tester) async {
        final bloc = FakeSearchBloc();

        await tester.pumpWidget(
          MaterialApp(
            home: MovieSearchScreen(bloc: bloc),
          ),
        );

        bloc.states.add(LoadingState());
        await tester.tap(find.byIcon(Icons.search));

        await tester.pump();

        expect(find.byType(CircularProgressIndicator), findsOneWidget);

        bloc.close();
      });

      testWidgets('sends search terms to the bloc on submit',
          (WidgetTester tester) async {
        const searchTerms = 'search terms';
        final bloc = FakeSearchBloc();

        await tester.pumpWidget(
          MaterialApp(
            home: MovieSearchScreen(bloc: bloc),
          ),
        );

        await tester.enterText(find.byType(TextField), searchTerms);
        await tester.tap(find.byIcon(Icons.search));

        await tester.pump();

        expect(bloc.events, equals([SearchEvent(searchTerms)]));
      });

      testWidgets('shows results on loaded state', (WidgetTester tester) async {
        const title = 'title';
        const year = 'year';

        final bloc = FakeSearchBloc();

        await tester.pumpWidget(
          MaterialApp(
            home: MovieSearchScreen(bloc: bloc),
          ),
        );

        bloc.states.add(LoadedState(const [Movie(title, year)]));
        await tester.tap(find.byIcon(Icons.search));

        await tester.pump();

        expect(find.byType(SearchResultTile), findsOneWidget);
        expect(find.text(title), findsOneWidget);
        expect(find.text(year), findsOneWidget);

        bloc.close();
      });

      testWidgets('shows snack bar on error state',
          (WidgetTester tester) async {
        const error = 'an error';
        final bloc = FakeSearchBloc();

        await tester.pumpWidget(
          MaterialApp(
            home: MovieSearchScreen(bloc: bloc),
          ),
        );

        expect(find.byType(SnackBar), findsNothing);

        bloc.states.add(ErrorState(error));

        await tester.tap(find.byIcon(Icons.search));

        await tester.pump();

        expect(find.byType(SnackBar), findsOneWidget);
        expect(find.text(error), findsOneWidget);

        bloc.close();
      });

      testWidgets('sends select event to bloc when clicking a movie tile',
          (WidgetTester tester) async {
        const title = 'title';
        final bloc = FakeSearchBloc();

        await tester.pumpWidget(
          MaterialApp(
            home: MovieSearchScreen(bloc: bloc),
          ),
        );

        bloc.states.add(LoadedState(const [Movie(title, '')]));
        await tester.tap(find.byIcon(Icons.search));

        await tester.pump();

        await tester.tap(find.byType(SearchResultTile));

        await tester.pumpAndSettle();

        expect(bloc.events, equals([SearchEvent(''), SelectEvent(title)]));

        bloc.close();
      });

      testWidgets('navigates to details screen when details have loaded',
          (WidgetTester tester) async {
        const title = 'title';
        const plot = 'what happened was this';
        const movieDetails = MovieDetails(title, '', '', plot);
        final bloc = FakeSearchBloc();

        await tester.pumpWidget(
          MaterialApp(
            home: MovieSearchScreen(bloc: bloc),
          ),
        );

        bloc.states.add(DetailsLoadedState(DetailsResult(movieDetails)));
        bloc.add(SelectEvent(title));

        await tester.pumpAndSettle();

        expect(find.byType(MovieDetailsScreen), findsOneWidget);
        expect(find.text(title), findsOneWidget);
        expect(find.text(plot), findsOneWidget);
        expect(find.byType(ImdbLink), findsOneWidget);

        bloc.close();
      });
    },
  );
}
