import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:equatable/equatable.dart';
import '../models/todo.dart';

class TodoState extends Equatable {
  final List<Todo> todos;

  TodoState(this.todos);

  @override
  List<Object> get props => [todos];
}

class TodoEvent extends Equatable {
  @override
  List<Object> get props => [];
}

class AddTodo extends TodoEvent {
  final String task;

  AddTodo(this.task);

  @override
  List<Object> get props => [task];
}

class ToggleTodo extends TodoEvent {
  final int index;

  ToggleTodo(this.index);

  @override
  List<Object> get props => [index];
}

class TodoBloc extends Bloc<TodoEvent, TodoState> {
  TodoBloc() : super(TodoState([]));

  @override
  Stream<TodoState> mapEventToState(TodoEvent event) async* {
    if (event is AddTodo) {
      final List<Todo> updatedTodos = List.from(state.todos)
        ..add(Todo(
          task: event.task,
          isCompleted: false,
        ));
      yield TodoState(updatedTodos);
    } else if (event is ToggleTodo) {
      final List<Todo> updatedTodos = List.from(state.todos);
      final Todo todo = updatedTodos[event.index];
      updatedTodos[event.index] = Todo(
        task: todo.task,
        isCompleted: !todo.isCompleted,
      );
      yield TodoState(updatedTodos);
    }
  }
}
