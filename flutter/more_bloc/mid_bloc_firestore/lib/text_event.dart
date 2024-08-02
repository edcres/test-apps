import 'package:equatable/equatable.dart';

abstract class TextEvent extends Equatable {
  const TextEvent();

  @override
  List<Object> get props => [];
}

class LoadText extends TextEvent {}

class SaveText extends TextEvent {
  final String text;

  const SaveText(this.text);

  @override
  List<Object> get props => [text];
}
