import 'package:flutter_test/flutter_test.dart';
import 'package:flutter_movies/omdb/omdb_results.dart';
import 'package:flutter_movies/omdb/omdb_api.dart';
import 'package:flutter_movies/omdb/omdb_service.dart';

void main() {
  const movieName = 'movie name';
  const searchUrl = '$baseUrl/?s=$movieName&apikey=$apiKey';
  const detailsUrl = '$baseUrl/?t=$movieName&plot=full&apikey=$apiKey';

  group('Omdb Service', () {
    test('searches api for provided search terms', () async {
      final service = OmdbService(api: FakeApi());
      final searchResult = await service.search(movieName);

      expect(searchResult, FakeResult(searchUrl, SearchResult.fromJson));
    });

    test('gets details from api for provided movie name', () async {
      final service = OmdbService(api: FakeApi());
      final searchResult = await service.getDetails(movieName);

      expect(searchResult, FakeResult(detailsUrl, DetailsResult.fromJson));
    });
  });
}

class FakeApi implements OmdbApi {
  @override
  Future<OmdbResult> callOmdb(String url, Function deserializeResult) =>
      Future.value(FakeResult(url, deserializeResult));
}

class FakeResult extends OmdbResult {
  final String url;

  final Function deserializeResult;

  FakeResult(this.url, this.deserializeResult);

  @override
  List<Object?> get props => [url, deserializeResult];
}
