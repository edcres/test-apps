import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import '../blocs/todo_bloc.dart';
import '../widgets/todo_item.dart';
import 'edit_todo_screen.dart';

class Chores extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return BlocBuilder<TodoBloc, TodoState>(
      builder: (context, state) {
        final chores = state.chores;

        return ListView.builder(
          itemCount: chores.length,
          itemBuilder: (context, index) {
            return TodoItem(
              todo: chores[index],
              onTap: () {
                context.read<TodoBloc>().add(ToggleChore(index));
              },
              onEdit: () {
                Navigator.of(context).push(MaterialPageRoute(
                  builder: (context) => EditTodoScreen(
                      index: index, todo: chores[index], isShoppingItem: false),
                ));
              },
            );
          },
        );
      },
    );
  }
}
