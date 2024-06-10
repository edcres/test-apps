import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import '../blocs/todo_bloc.dart';
import '../widgets/todo_item.dart';
import 'edit_todo_screen.dart';

class ItemsScreen extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return BlocBuilder<TodoBloc, TodoState>(
      builder: (context, state) {
        final items = state.items;

        return ListView.builder(
          itemCount: items.length,
          itemBuilder: (context, index) {
            return TodoItem(
              todo: items[index],
              onTap: () {
                context.read<TodoBloc>().add(ToggleItem(index));
              },
              onEdit: () {
                Navigator.of(context).push(MaterialPageRoute(
                  builder: (context) =>
                      EditTodoScreen(index: index, todo: items[index]),
                ));
              },
            );
          },
        );
      },
    );
  }
}
