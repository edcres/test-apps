import 'todo.dart';

class ChoreItem extends Todo {
  ChoreItem({
    required String task,
    required bool isCompleted,
  }) : super(task: task, isCompleted: isCompleted, itemType: ItemType.Chore);

  @override
  List<Object> get props => [task, isCompleted, itemType];
}
