import 'package:flutter/material.dart';
import '../models/todo.dart';

class TodoItem extends StatelessWidget {
  final Todo todo;
  final VoidCallback onTap;

  TodoItem({required this.todo, required this.onTap});

  @override
  Widget build(BuildContext context) {
    return ListTile(
      title: Text(
        todo.task,
        style: TextStyle(
          decoration: todo.isCompleted ? TextDecoration.lineThrough : null,
        ),
      ),
      trailing: Icon(
        todo.isCompleted ? Icons.check_box : Icons.check_box_outline_blank,
      ),
      onTap: onTap,
    );
  }
}
