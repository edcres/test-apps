import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:equatable/equatable.dart';
import '../models/todo.dart';

// Event Definitions
abstract class TodoEvent extends Equatable {
  @override
  List<Object> get props => [];
}

class AddShoppingItem extends TodoEvent {
  final String item;

  AddShoppingItem(this.item);

  @override
  List<Object> get props => [item];
}

class AddChore extends TodoEvent {
  final String chore;

  AddChore(this.chore);

  @override
  List<Object> get props => [chore];
}

class ToggleShoppingItem extends TodoEvent {
  final int index;

  ToggleShoppingItem(this.index);

  @override
  List<Object> get props => [index];
}

class ToggleChore extends TodoEvent {
  final int index;

  ToggleChore(this.index);

  @override
  List<Object> get props => [index];
}

// State Definition
class TodoState extends Equatable {
  final List<Todo> shoppingItems;
  final List<Todo> chores;

  TodoState({required this.shoppingItems, required this.chores});

  @override
  List<Object> get props => [shoppingItems, chores];
}

// BLoC Definition
class TodoBloc extends Bloc<TodoEvent, TodoState> {
  TodoBloc() : super(TodoState(shoppingItems: [], chores: [])) {
    on<AddShoppingItem>((event, emit) {
      final List<Todo> updatedShoppingItems = List.from(state.shoppingItems)
        ..add(Todo(
          task: event.item,
          isCompleted: false,
        ));
      emit(
          TodoState(shoppingItems: updatedShoppingItems, chores: state.chores));
    });

    on<AddChore>((event, emit) {
      final List<Todo> updatedChores = List.from(state.chores)
        ..add(Todo(
          task: event.chore,
          isCompleted: false,
        ));
      emit(
          TodoState(shoppingItems: state.shoppingItems, chores: updatedChores));
    });

    on<ToggleShoppingItem>((event, emit) {
      final List<Todo> updatedShoppingItems = List.from(state.shoppingItems);
      final Todo item = updatedShoppingItems[event.index];
      updatedShoppingItems[event.index] = Todo(
        task: item.task,
        isCompleted: !item.isCompleted,
      );
      emit(
          TodoState(shoppingItems: updatedShoppingItems, chores: state.chores));
    });

    on<ToggleChore>((event, emit) {
      final List<Todo> updatedChores = List.from(state.chores);
      final Todo chore = updatedChores[event.index];
      updatedChores[event.index] = Todo(
        task: chore.task,
        isCompleted: !chore.isCompleted,
      );
      emit(
          TodoState(shoppingItems: state.shoppingItems, chores: updatedChores));
    });
  }
}
