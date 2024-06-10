// import 'package:flutter/material.dart';
// import 'package:flutter_bloc/flutter_bloc.dart';
// import '../blocs/todo_bloc.dart';
// import '../widgets/todo_item.dart';
// import 'edit_todo_screen.dart';

// class ShoppingItems extends StatelessWidget {
//   @override
//   Widget build(BuildContext context) {
//     return BlocBuilder<TodoBloc, TodoState>(
//       builder: (context, state) {
//         final shoppingItems = state.shoppingItems;

//         return ListView.builder(
//           itemCount: shoppingItems.length,
//           itemBuilder: (context, index) {
//             return TodoItem(
//               todo: shoppingItems[index],
//               onTap: () {
//                 context.read<TodoBloc>().add(ToggleShoppingItem(index));
//               },
//               onEdit: () {
//                 Navigator.of(context).push(MaterialPageRoute(
//                   builder: (context) => EditTodoScreen(
//                       index: index,
//                       todo: shoppingItems[index],
//                       isShoppingItem: true),
//                 ));
//               },
//             );
//           },
//         );
//       },
//     );
//   }
// }
