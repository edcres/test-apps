import 'package:flutter/material.dart';

void main() => runApp(MyApp());

// Main widget for the application. It sets up the basic theme and title.
class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Simple To-Do List',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: TodoListScreen(),
    );
  }
}

// Main screen for the to-do list. It manages the state of the list.
class TodoListScreen extends StatefulWidget {
  @override
  _TodoListScreenState createState() => _TodoListScreenState();
}

// A stateful widget class that manages the state and behavior of the TodoListScreen
class _TodoListScreenState extends State<TodoListScreen> {
  final List<String> _todoItems = [];

  // Method adds a new item to the list if the input is not empty.
  void _addTodoItem(String task) {
    if (task.isNotEmpty) {
      setState(() => _todoItems.add(task));
    }
  }

  // Method shows a dialog to input a new item.
  void _promptAddTodoItem() {
    showDialog(
      context: context,
      builder: (BuildContext context) {
        return AlertDialog(
          title: Text('New task'),
          content: TextField(
            autofocus: true,
            onSubmitted: (val) {
              Navigator.pop(context);
              _addTodoItem(val);
            },
          ),
          actions: <Widget>[
            TextButton(
              child: Text('Cancel'),
              onPressed: () => Navigator.pop(context),
            ),
          ],
        );
      },
    );
  }

  // Method builds the list view for the to-do items.
  Widget _buildTodoList() {
    return ListView.builder(
      itemBuilder: (context, index) {
        if (index < _todoItems.length) {
          return _buildTodoItem(_todoItems[index], index);
        }
        return null;
      },
    );
  }

  // Method builds each individual to-do item as a list tile.
  Widget _buildTodoItem(String todoText, int index) {
    return ListTile(
      title: Text(todoText),
    );
  }

// Responsible for constructing the user interface of the TodoListScreen.
  @override
  Widget build(BuildContext context) {
    // Scaffold: Provides the basic structure for the visual interface,
    //   including the app bar, body, and floating action button.
    return Scaffold(
      appBar: AppBar(
        title: Text('Simple To-Do List'),
      ),
      body: _buildTodoList(),
      floatingActionButton: FloatingActionButton(
        onPressed: _promptAddTodoItem,
        tooltip: 'Add task',
        child: Icon(Icons.add),
      ),
    );
  }
}
