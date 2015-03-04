import 'package:flutter/material.dart';
import 'package:flutter_movies/search/search_bloc.dart';

import 'events.dart';

class SearchInput extends StatefulWidget {
  final _controller = TextEditingController();
  final SearchBloc _bloc;

  SearchInput(SearchBloc bloc, {super.key})
      : _bloc = bloc;

  @override
  State<SearchInput> createState() => _SearchInputState();
}

class _SearchInputState extends State<SearchInput> {
  @override
  Widget build(BuildContext context) {
    return TextField(
      controller: widget._controller,
      decoration: InputDecoration(
        fillColor: Theme.of(context).primaryColor,
        filled: true,
        border: const OutlineInputBorder(),
        counterText: '',
        prefixIcon: IconButton(
          onPressed: () => _sendSearchEvent(widget._controller.text),
          icon: const Icon(Icons.search, color: Colors.black),
        ),
      ),
      cursorColor: Colors.black,
      maxLength: _maxTitleLength,
      maxLines: 1,
      textInputAction: TextInputAction.search,
      onSubmitted: (text) => _sendSearchEvent(text),
    );
  }

  void _sendSearchEvent(String text) => widget._bloc.add(SearchEvent(text));
}

const _maxTitleLength = 184;
