import 'package:equatable/equatable.dart';

abstract class TextState extends Equatable {
  const TextState();

  @override
  List<Object> get props => [];
}

class TextInitial extends TextState {}

class TextLoading extends TextState {}

class TextLoaded extends TextState {
  final String text;

  const TextLoaded(this.text);

  @override
  List<Object> get props => [text];
}

class TextSaved extends TextState {}

class TextError extends TextState {
  final String message;

  const TextError(this.message);

  @override
  List<Object> get props => [message];
}
