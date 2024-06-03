import 'package:flutter/material.dart';

void main() => runApp(MyApp());

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

class TodoListScreen extends StatefulWidget {
  @override
  _TodoListScreenState createState() => _TodoListScreenState();
}

class _TodoListScreenState extends State<TodoListScreen> {
  final List<String> _todoItems = [];
  final GlobalKey<AnimatedListState> _listKey = GlobalKey<AnimatedListState>();

  void _addTodoItem(String task) {
    if (task.isNotEmpty) {
      setState(() {
        _todoItems.add(task);
        _listKey.currentState!.insertItem(_todoItems.length - 1);
      });
    }
  }

  void _removeTodoItem(int index) {
    String removedItem = _todoItems.removeAt(index);
    _listKey.currentState!.removeItem(
      index,
      (context, animation) => _buildTodoItem(removedItem, index, animation),
      duration: Duration(milliseconds: 300),
    );
  }

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

  Widget _buildTodoList() {
    return AnimatedList(
      key: _listKey,
      initialItemCount: _todoItems.length,
      itemBuilder: (context, index, animation) {
        return _buildTodoItem(_todoItems[index], index, animation);
      },
    );
  }

  Widget _buildTodoItem(
      String todoText, int index, Animation<double> animation) {
    // Change the animations for list items (add/remove).
    return SizeTransition(
      sizeFactor: animation,
      child: Card(
        margin: EdgeInsets.symmetric(vertical: 4.0, horizontal: 8.0),
        elevation: 2.0,
        child: ListTile(
          title: Text(todoText),
          trailing: IconButton(
            icon: Icon(Icons.delete),
            onPressed: () => _removeTodoItem(index),
          ),
        ),
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
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
