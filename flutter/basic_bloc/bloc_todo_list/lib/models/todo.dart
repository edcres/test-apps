import 'package:equatable/equatable.dart';

enum ItemType { Shopping, Chore }

abstract class Todo extends Equatable {
  final String task;
  final bool isCompleted;
  final ItemType itemType;

  Todo({
    required this.task,
    required this.isCompleted,
    required this.itemType,
  });

  @override
  List<Object> get props => [task, isCompleted, itemType];
}
