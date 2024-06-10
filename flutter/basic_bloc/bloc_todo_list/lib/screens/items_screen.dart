import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import '../blocs/todo_bloc.dart';
import '../widgets/todo_item.dart';
import 'edit_todo_screen.dart';
import '../models/todo.dart';

class ItemsScreen extends StatelessWidget {
  final ItemType itemType;

  ItemsScreen({required this.itemType});

  @override
  Widget build(BuildContext context) {
    return BlocBuilder<TodoBloc, TodoState>(
      builder: (context, state) {
        final items =
            state.items.where((item) => item.itemType == itemType).toList();

        return ListView.builder(
          itemCount: items.length,
          itemBuilder: (context, index) {
            return TodoItem(
              todo: items[index],
              onTap: () {
                context
                    .read<TodoBloc>()
                    .add(ToggleItem(state.items.indexOf(items[index])));
              },
              onEdit: () {
                Navigator.of(context).push(MaterialPageRoute(
                  builder: (context) => EditTodoScreen(
                      index: state.items.indexOf(items[index]),
                      todo: items[index]),
                ));
              },
            );
          },
        );
      },
    );
  }
}
