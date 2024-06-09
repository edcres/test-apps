import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import '../blocs/todo_bloc.dart';
import '../widgets/todo_item.dart';

class PendingTodos extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return BlocBuilder<TodoBloc, TodoState>(
      builder: (context, state) {
        final pendingTodos =
            state.todos.where((todo) => !todo.isCompleted).toList();

        return ListView.builder(
          itemCount: pendingTodos.length,
          itemBuilder: (context, index) {
            return TodoItem(
              todo: pendingTodos[index],
              onTap: () {
                context.read<TodoBloc>().add(ToggleTodo(
                      state.todos.indexOf(pendingTodos[index]),
                    ));
              },
            );
          },
        );
      },
    );
  }
}
