import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import '../blocs/todo_bloc.dart';
import '../widgets/todo_item.dart';

class CompletedTodos extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return BlocBuilder<TodoBloc, TodoState>(
      builder: (context, state) {
        final completedTodos =
            state.todos.where((todo) => todo.isCompleted).toList();

        return ListView.builder(
          itemCount: completedTodos.length,
          itemBuilder: (context, index) {
            return TodoItem(
              todo: completedTodos[index],
              onTap: () {
                context.read<TodoBloc>().add(ToggleTodo(
                      state.todos.indexOf(completedTodos[index]),
                    ));
              },
            );
          },
        );
      },
    );
  }
}
