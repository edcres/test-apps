import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:equatable/equatable.dart';
import '../models/todo.dart';
import '../models/shopping_item.dart';
import '../models/chore_item.dart';

// Event Definitions
abstract class TodoEvent extends Equatable {
  @override
  List<Object> get props => [];
}

class AddItem extends TodoEvent {
  final String item;
  final ItemType itemType;

  AddItem(this.item, this.itemType);

  @override
  List<Object> get props => [item, itemType];
}

class ToggleItem extends TodoEvent {
  final int index;

  ToggleItem(this.index);

  @override
  List<Object> get props => [index];
}

class UpdateItem extends TodoEvent {
  final int index;
  final String updatedTask;

  UpdateItem(this.index, this.updatedTask);

  @override
  List<Object> get props => [index, updatedTask];
}

// State Definition
class TodoState extends Equatable {
  final List<Todo> items;

  TodoState({required this.items});

  @override
  List<Object> get props => [items];
}

// BLoC Definition
class TodoBloc extends Bloc<TodoEvent, TodoState> {
  TodoBloc() : super(TodoState(items: [])) {
    on<AddItem>((event, emit) {
      final List<Todo> updatedItems = List.from(state.items)
        ..add(event.itemType == ItemType.Shopping
            ? ShoppingItem(task: event.item, isCompleted: false)
            : ChoreItem(task: event.item, isCompleted: false));
      emit(TodoState(items: updatedItems));
    });

    on<ToggleItem>((event, emit) {
      final List<Todo> updatedItems = List.from(state.items);
      final Todo item = updatedItems[event.index];
      updatedItems[event.index] = item.itemType == ItemType.Shopping
          ? ShoppingItem(task: item.task, isCompleted: !item.isCompleted)
          : ChoreItem(task: item.task, isCompleted: !item.isCompleted);
      emit(TodoState(items: updatedItems));
    });

    on<UpdateItem>((event, emit) {
      final List<Todo> updatedItems = List.from(state.items);
      final Todo item = updatedItems[event.index];
      updatedItems[event.index] = item.itemType == ItemType.Shopping
          ? ShoppingItem(task: event.updatedTask, isCompleted: item.isCompleted)
          : ChoreItem(task: event.updatedTask, isCompleted: item.isCompleted);
      emit(TodoState(items: updatedItems));
    });
  }
}
