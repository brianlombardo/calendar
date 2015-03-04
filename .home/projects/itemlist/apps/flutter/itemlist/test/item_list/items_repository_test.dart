import 'package:flutter/foundation.dart';
import 'package:http/http.dart';
import 'package:item_list/api/item_data.dart';
import 'package:item_list/item_list/items_repository.dart';
import 'package:mockito/mockito.dart';
import 'package:test/test.dart';

import '../mocks.dart';

void main() {
  ItemsRepository testObject;
  Client mockClient;
  Response mockResponse;

  final expectedUri = Uri.parse(SERVER);
  final expectedHeaders = HEADERS;
  final getResponse = '''
      [
        {
          "id": "1",
          "text": "aloha"
        },
        {
          "id": "2",
          "text": "ciao"
        }
      ]
    ''';
  setUp(() {
    mockClient = MockHttpClient();
    mockResponse = MockResponse();
    when(mockClient.get(
      any,
      headers: anyNamed('headers'),
    )).thenAnswer((_) async => mockResponse);

    when(mockResponse.body).thenReturn(getResponse);

    testObject = ItemsRepository(client: mockClient);
  });

  group('fetching', () {
    test('should GET items from server', () {
      testObject.getItems();

      verify(mockClient.get(expectedUri, headers: expectedHeaders));
    });

    test('should return a list of items', () async {
      final actual = await testObject.getItems();

      var expected = [
        ItemData(id: '1', text: 'aloha'),
        ItemData(id: '2', text: 'ciao'),
      ];

      expect(listEquals(actual, expected), isTrue);
    });
  });

  group('creating', () {
    test('should POST items to server', () {
      testObject.createItem('item text!!!');

      final expectedBody = '{"id":null,"text":"item text!!!"}';

      verify(
        mockClient.post(expectedUri,
            headers: expectedHeaders, body: expectedBody),
      );
    });
  });
  group('deleting', () {
    test('should DELETE items from server', () {
      testObject.deleteItem('ID');

      verify(
        mockClient.delete(Uri.parse('$SERVER/ID'), headers: expectedHeaders),
      );
    });
  });
}
