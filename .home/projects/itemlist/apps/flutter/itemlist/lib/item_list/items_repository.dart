import 'dart:convert';

import 'package:http/http.dart' as http;
import 'package:item_list/api/item_data.dart';

const SERVER = 'http://localhost:3000/api/v1/items';
const HEADERS = {'Content-Type': 'application/json'};

class ItemsRepository {
  final http.Client _client;

  ItemsRepository({http.Client client}) : _client = client ?? http.Client();

  Future<List<ItemData>> getItems() async {
    var res = await _client.get(Uri.parse(SERVER), headers: HEADERS);

    return (json.decode(res.body) as List<dynamic>)
        .map((e) => ItemData.fromJson(e))
        .toList();
  }

  Future<http.Response> createItem(String item) async {
    return _client.post(
      Uri.parse(SERVER),
      headers: HEADERS,
      body: jsonEncode(ItemData(text: item).toJson()),
    );
  }

  Future<http.Response> deleteItem(String id) {
    return _client.delete(
      Uri.parse('$SERVER/$id'),
      headers: HEADERS,
    );
  }
}
