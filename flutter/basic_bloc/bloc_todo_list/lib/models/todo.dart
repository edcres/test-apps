import 'package:bloc_todo_list/blocs/todo_bloc.dart';

class Todo {
  final String task;
  final bool isCompleted;
  final ItemType itemType;

  Todo({
    required this.task,
    required this.isCompleted,
    required this.itemType,
  });
}
