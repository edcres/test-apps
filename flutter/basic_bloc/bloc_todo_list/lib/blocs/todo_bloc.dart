import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:equatable/equatable.dart';
import '../models/todo.dart';

// Event Definitions
abstract class TodoEvent extends Equatable {
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

// State Definition
class TodoState extends Equatable {
  final List<Todo> todos;

  TodoState(this.todos);

  @override
  List<Object> get props => [todos];
}

// BLoC Definition
class TodoBloc extends Bloc<TodoEvent, TodoState> {
  TodoBloc() : super(TodoState([])) {
    on<AddTodo>((event, emit) {
      final List<Todo> updatedTodos = List.from(state.todos)
        ..add(Todo(
          task: event.task,
          isCompleted: false,
        ));
      emit(TodoState(updatedTodos));
    });

    on<ToggleTodo>((event, emit) {
      final List<Todo> updatedTodos = List.from(state.todos);
      final Todo todo = updatedTodos[event.index];
      updatedTodos[event.index] = Todo(
        task: todo.task,
        isCompleted: !todo.isCompleted,
      );
      emit(TodoState(updatedTodos));
    });
  }
}
