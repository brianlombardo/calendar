import 'dart:io';
import 'dart:convert';

void main() async {
  final server = await HttpServer.bind(InternetAddress.loopbackIPv4, 3000);
  print('Server listening on port 3000');

  await for (HttpRequest request in server) {
    if (request.method case 'GET') {
      if (request.uri.path == '/search') {
        handleSearch(request);
      } else if (request.uri.path == '/getDetails') {
        handleGetDetails(request);
      }
    } else {
      handleNotFound(request);
    }
  }
}

void handleSearch(HttpRequest request) {
  final query = request.uri.queryParameters['query'];
  if (query == null) {
    sendResponse(request, 400, {'error': 'Missing query parameter'});
    return;
  }

  final results = {'results': 'Search results for: $query'};
  sendResponse(request, 200, results);
}

void handleGetDetails(HttpRequest request) {
  final query = request.uri.queryParameters['query'];
  if (query == null) {
    sendResponse(request, 400, {'error': 'Missing query parameter'});
    return;
  }

  final details = {'details': 'Details for: $query'};
  sendResponse(request, 200, details);
}

void handleNotFound(HttpRequest request) {
  sendResponse(request, 404, {'error': 'Not found'});
}

void sendResponse(HttpRequest request, int statusCode, Map<String, dynamic> body) {
  request.response
    ..statusCode = statusCode
    ..headers.contentType = ContentType.json
    ..write(json.encode(body))
    ..close();
}