import 'package:equatable/equatable.dart';

abstract class DataEvent extends Equatable {
  @override
  List<Object> get props => [];
}

class FetchDataEvent extends DataEvent {}
