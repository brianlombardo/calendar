import 'package:http/http.dart';
import 'package:item_list/item_list/items_repository.dart';
import 'package:mockito/mockito.dart';

class MockHttpClient extends Mock implements Client {}

class MockResponse extends Mock implements Response {}

class MockRepo extends Mock implements ItemsRepository {}
