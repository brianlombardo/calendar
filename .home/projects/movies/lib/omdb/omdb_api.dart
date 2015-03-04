import 'dart:convert';

import 'package:http/http.dart';

import 'omdb_results.dart';

class OmdbApi {
  final Client _client;
  final _genericError = ErrorResult(genericErrorMessage);

  OmdbApi({Client? client}) : _client = client ?? Client();

  Future<OmdbResult> callOmdb(String url, Function deserializeResult) async {
    try {
      final Response response = await _client.get(Uri.parse(url));

      if (response.statusCode == 200) {
        final body = json.decode(response.body);
        final error = body['Error'];
        if (error != null) {
          return ErrorResult(error.toString());
        }
        return deserializeResult(body);
      }
    } catch (_) {
      return _genericError;
    }
    return _genericError;
  }
}

const genericErrorMessage = 'Something went wrong.';
