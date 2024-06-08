import 'package:equatable/equatable.dart';

abstract class DataState extends Equatable {
  @override
  List<Object> get props => [];
}

class InitialState extends DataState {}

class LoadingState extends DataState {}

class LoadedState extends DataState {
  final List<String> data;
  LoadedState(this.data);

  @override
  List<Object> get props => [data];
}

class ErrorState extends DataState {
  final String message;
  ErrorState(this.message);

  @override
  List<Object> get props => [message];
}
