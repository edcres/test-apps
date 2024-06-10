import 'todo.dart';

class ShoppingItem extends Todo {
  ShoppingItem({
    required String task,
    required bool isCompleted,
  }) : super(task: task, isCompleted: isCompleted, itemType: ItemType.Shopping);

  @override
  List<Object> get props => [task, isCompleted, itemType];
}
