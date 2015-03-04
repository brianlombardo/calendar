import 'dart:convert';
import 'dart:typed_data';

import 'package:http/http.dart';

class FakeClient implements Client {
  final Future<Response>? _getResponse;

  final Uri _getUrl;

  FakeClient({Response? respondsWith, String? whenCalling})
      : _getResponse = respondsWith != null ? Future.value(respondsWith) : null,
        _getUrl = Uri.parse(whenCalling ?? '');

  @override
  Future<Response> get(Uri url, {Map<String, String>? headers}) {
    if (url != _getUrl) throw IncorrectUrlError();
    return _getResponse!;
  }

  @override
  Future<Response> delete(Uri url,
          {Map<String, String>? headers, Object? body, Encoding? encoding}) =>
      _stub();

  @override
  Future<Response> head(Uri url, {Map<String, String>? headers}) => _stub();

  @override
  Future<Response> patch(Uri url,
          {Map<String, String>? headers, Object? body, Encoding? encoding}) =>
      _stub();

  @override
  Future<Response> post(Uri url,
          {Map<String, String>? headers, Object? body, Encoding? encoding}) =>
      _stub();

  @override
  Future<Response> put(Uri url,
          {Map<String, String>? headers, Object? body, Encoding? encoding}) =>
      _stub();

  @override
  Future<String> read(Uri url, {Map<String, String>? headers}) => _stub();

  @override
  Future<Uint8List> readBytes(Uri url, {Map<String, String>? headers}) =>
      _stub();

  @override
  Future<StreamedResponse> send(BaseRequest request) => _stub();

  @override
  void close() {}

  Never _stub() => throw UnimplementedError();
}

class IncorrectUrlError extends Error {}
