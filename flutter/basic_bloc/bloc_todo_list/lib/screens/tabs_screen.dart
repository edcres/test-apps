import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import '../blocs/todo_bloc.dart';
import 'items_screen.dart';

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
              Tab(text: 'Shopping'),
              Tab(text: 'Chores'),
            ],
          ),
        ),
        body: TabBarView(
          children: [
            ItemsScreen(),
            ItemsScreen(),
          ],
        ),
        floatingActionButton: FloatingActionButton(
          onPressed: () {
            showDialog(
              context: context,
              builder: (context) {
                return AddItemDialog();
              },
            );
          },
          child: Icon(Icons.add),
        ),
      ),
    );
  }
}

class AddItemDialog extends StatefulWidget {
  @override
  _AddItemDialogState createState() => _AddItemDialogState();
}

class _AddItemDialogState extends State<AddItemDialog> {
  final _controller = TextEditingController();

  @override
  Widget build(BuildContext context) {
    return AlertDialog(
      title: Text('Add Item'),
      content: TextField(
        controller: _controller,
        decoration: InputDecoration(hintText: 'Enter item here'),
      ),
      actions: [
        TextButton(
          onPressed: () {
            final item = _controller.text;
            if (item.isNotEmpty) {
              context.read<TodoBloc>().add(AddItem(item));
            }
            Navigator.of(context).pop();
          },
          child: Text('Add'),
        ),
      ],
    );
  }
}
