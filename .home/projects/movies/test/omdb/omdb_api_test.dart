import 'package:flutter_movies/omdb/movie_models.dart';
import 'package:flutter_movies/omdb/omdb_api.dart';
import 'package:flutter_movies/omdb/omdb_results.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:http/http.dart';

import 'fake_client.dart';

void main() {
  const validSearchResponse = '''
{
  "Search": [
    {
      "Title": "title",
      "Year": "year",
      "imdbID": "imdb",
      "Type": "movie",
      "Poster": "poster"
    }
  ]
}
''';

  const validDetailResponse = '''
{
  "Title": "title",
  "Year": "year",
  "imdbID": "imdb",
  "Plot": "plot"
}
''';

  const errorResponse =
      '{"Response":"response","Error":"a helpful message from OMDB"}';

  const url = 'url';

  group('Omdb Api', () {
    test('returns generic error when request fails', () async {
      final searchResult =
          await OmdbApi(client: FakeClient()).callOmdb(url, () {});

      expect(searchResult, ErrorResult(genericErrorMessage));
    });

    test('returns generic error when response code is not 200', () async {
      final searchResult = await OmdbApi(
          client: FakeClient(
        respondsWith: Response('', 123),
        whenCalling: url,
      )).callOmdb(url, () {});

      expect(searchResult, ErrorResult(genericErrorMessage));
    });

    test('returns errors found in response with code 200', () async {
      final searchResult = await OmdbApi(
          client: FakeClient(
        respondsWith: Response(errorResponse, 200),
        whenCalling: url,
      )).callOmdb(url, () {});

      expect(searchResult, ErrorResult('a helpful message from OMDB'));
    });

    test('returns successful search results', () async {
      final searchResult = await OmdbApi(
        client: FakeClient(
          respondsWith: Response(validSearchResponse, 200),
          whenCalling: url,
        ),
      ).callOmdb(url, SearchResult.fromJson);

      expect(searchResult,
          SearchResult(resultList: const [Movie('title', 'year')]));
    });

    test('returns successful details results', () async {
      final detailsResult = await OmdbApi(
        client: FakeClient(
          respondsWith: Response(validDetailResponse, 200),
          whenCalling: url,
        ),
      ).callOmdb(url, DetailsResult.fromJson);

      expect(
          detailsResult,
          DetailsResult(const MovieDetails(
              'title', 'year', 'https://www.imdb.com/title/imdb', 'plot')));
    });
  });
}
