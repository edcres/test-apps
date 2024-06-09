import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import '../blocs/todo_bloc.dart';
import 'completed_todos.dart';
import 'pending_todos.dart';

class TabsScreen extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return DefaultTabController(
      length: 2,
      child: Scaffold(
        appBar: AppBar(
          title: Text('To-Do List'),
          bottom: TabBar(
            tabs: [
              Tab(text: 'Pending'),
              Tab(text: 'Completed'),
            ],
          ),
        ),
        body: TabBarView(
          children: [
            PendingTodos(),
            CompletedTodos(),
          ],
        ),
        floatingActionButton: FloatingActionButton(
          onPressed: () {
            showDialog(
              context: context,
              builder: (context) {
                return AddTodoDialog();
              },
            );
          },
          child: Icon(Icons.add),
        ),
      ),
    );
  }
}

class AddTodoDialog extends StatefulWidget {
  @override
  _AddTodoDialogState createState() => _AddTodoDialogState();
}

class _AddTodoDialogState extends State<AddTodoDialog> {
  final _controller = TextEditingController();

  @override
  Widget build(BuildContext context) {
    return AlertDialog(
      title: Text('Add To-Do'),
      content: TextField(
        controller: _controller,
        decoration: InputDecoration(hintText: 'Enter task here'),
      ),
      actions: [
        TextButton(
          onPressed: () {
            final task = _controller.text;
            if (task.isNotEmpty) {
              context.read<TodoBloc>().add(AddTodo(task));
            }
            Navigator.of(context).pop();
          },
          child: Text('Add'),
        ),
      ],
    );
  }
}
