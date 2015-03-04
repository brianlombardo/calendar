import 'package:equatable/equatable.dart';

class ItemData extends Equatable {
  final String id;
  final String text;

  ItemData({this.id, this.text});

  Map<String, dynamic> toJson() => {
        'id': id,
        'text': text,
      };

  factory ItemData.fromJson(Map<String, dynamic> json) =>
      ItemData(id: json['id'], text: json['text']);

  @override
  List<Object> get props => [id, text];
}
