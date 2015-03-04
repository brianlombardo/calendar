import 'package:bloc_test/bloc_test.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:item_list/api/item_data.dart';
import 'package:item_list/item_list/events.dart';
import 'package:item_list/item_list/items_bloc.dart';
import 'package:item_list/item_list/items_repository.dart';
import 'package:item_list/item_list/states.dart';
import 'package:item_list/model/item_model.dart';
import 'package:mockito/mockito.dart';

import '../mocks.dart';

void main() {
  ItemsRepository mockRepo;

  setUp(() {
    mockRepo = MockRepo();
  });

  group('Items bloc', () {
    blocTest(
      'emits initial state',
      build: () async => ConnectedItemsBloc(repo: mockRepo),
      skip: 0,
      expect: [Initial()],
    );

    final testItemText = 'Test';

    blocTest(
      'adds item to list',
      build: () async {
        when(mockRepo.getItems()).thenAnswer((_) async => [
              ItemData(
                text: testItemText,
                id: 'id',
              )
            ]);

        return ConnectedItemsBloc(repo: mockRepo);
      },
      skip: 0,
      act: (bloc) async => bloc.add(AddItem(testItemText)),
      expect: [
        Initial(),
        Loaded([Item(text: testItemText, pending: true)]),
        Loaded([Item(text: testItemText, pending: false, id: 'id')])
      ],
      verify: (_) {
        verify(mockRepo.createItem(testItemText));
        return;
      },
    );

    final item = Item(id: 'ID', text: testItemText);
    blocTest(
      'deletes item from list',
      build: () async {
        when(mockRepo.getItems()).thenAnswer((_) async => []);

        return ConnectedItemsBloc(repo: mockRepo);
      },
      skip: 0,
      act: (bloc) async => bloc.add(DeleteItem(item.id)),
      expect: [Initial(), Loaded([])],
      verify: (_) {
        verify(mockRepo.deleteItem(item.id));
        return;
      },
    );

    blocTest(
      'refreshes the list',
      build: () async {
        when(mockRepo.getItems()).thenAnswer((_) async => []);

        return ConnectedItemsBloc(repo: mockRepo);
      },
      skip: 0,
      act: (bloc) async => bloc.add(RefreshItems()),
      expect: [Initial(), Loaded([])],
      verify: (_) {
        verify(mockRepo.getItems());
        return;
      },
    );

    final unsortedItem0 = ItemData(text: 'item', id: '0');
    final unsortedItem1 = ItemData(text: 'item', id: '1');
    final unsortedItem2 = ItemData(text: 'item', id: '2');

    blocTest(
      'sorts the list in descending order by id',
      build: () async {
        when(mockRepo.getItems()).thenAnswer((_) async => [
              unsortedItem1,
              unsortedItem0,
              unsortedItem2,
            ]);

        return ConnectedItemsBloc(repo: mockRepo);
      },
      skip: 0,
      act: (bloc) async => bloc.add(RefreshItems()),
      expect: [
        Initial(),
        Loaded([
          Item(text: 'item', id: '2'),
          Item(text: 'item', id: '1'),
          Item(text: 'item', id: '0'),
        ])
      ],
    );

    final blankItemText = ' ';

    blocTest(
      'emits an error when added item is blank',
      build: () async {
        when(mockRepo.getItems()).thenAnswer((_) async => []);
        return ConnectedItemsBloc(repo: mockRepo);
      },
      skip: 0,
      act: (bloc) => bloc.add(AddItem(blankItemText)),
      expect: [
        Initial(),
        Error('Item text cannot be blank'),
      ],
      verify: (_) {
        verifyZeroInteractions(mockRepo);
        return;
      },
    );
  });
}
