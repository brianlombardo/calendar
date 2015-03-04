import 'omdb_api.dart';
import 'omdb_results.dart';

class OmdbService {
  final OmdbApi _api;

  OmdbService({OmdbApi? api}) : _api = api ?? OmdbApi();

  Future<OmdbResult> search(String query) async =>
      _api.callOmdb('$baseUrl/?s=$query&apikey=$apiKey', SearchResult.fromJson);

  Future<OmdbResult> getDetails(String query) async => _api.callOmdb(
      '$baseUrl/?t=$query&plot=full&apikey=$apiKey', DetailsResult.fromJson);
}

const baseUrl = 'http://localhost:3000'; // http://www.omdbapi.com
const apiKey = 'test';
