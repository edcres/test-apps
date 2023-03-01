import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_bloc_concepts/logic/presentation/screens/home_screen.dart';
import 'logic/cubit/counter_cubit.dart';
import 'logic/presentation/screens/second_screen.dart';
import 'logic/presentation/screens/third_screen.dart';

// Goal: locally provide a single instance of the counter cubit (cubit fot the counter feature) to the default home screen
//    have to dispose it manually and use a stateful widget instead os stateless (use the dispose function)

void main() {
  runApp(MyApp());
}

class MyApp extends StatefulWidget {
  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  final CounterCubit _counterCubit = CounterCubit();

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
        visualDensity: VisualDensity.adaptivePlatformDensity,
      ),
      routes: {
        '/': (context) => BlocProvider.value(
              create: (context) => _counterCubit,
              child: HomeScreen(
                title: 'HomeScreen',
                color: Colors.blueAccent,
              ),
            ),
        '/second': (context) => BlocProvider.value(
              create: (context) => _counterCubit,
              child: SecondScreen(
                title: 'SecondScreen',
                color: Colors.redAccent,
              ),
            ),
        '/third': (context) => BlocProvider.value(
              create: (context) => _counterCubit,
              child: ThirdScreen(
                title: 'ThirdScreen',
                color: Colors.greenAccent,
              ),
            ),
      },
      // home: BlocProvider<CounterCubit>(
      //   create: (context) => CounterCubit(),
      //   // route (screen) the flutter pushes by default and in the
      //   child: HomeScreen(
      //     title: 'Flutter Demo Home Page',
      //     color: Colors.blueAccent,
      //   ),
      // ),
    );
  }

  @override
  void dispose() {
    _counterCubit.close();
    super.dispose();
  }
}

// class HomeScreen extends StatefulWidget {
//   HomeScreen({Key key, this.title}) : super(key: key);

//   final String title;

//   @override
//   _HomeScreenState createState() => _HomeScreenState();
// }

// class _HomeScreenState extends State<HomeScreen> {
//   @override
//   Widget build(BuildContext context) {
//     return Scaffold(
//       appBar: AppBar(
//         title: Text(widget.title),
//       ),
//       body: Center(
//         child: Column(
//           mainAxisAlignment: MainAxisAlignment.center,
//           children: <Widget>[
//             Text(
//               'You have pushed the button this many times:',
//             ),
//             BlocConsumer<CounterCubit, CounterState>(
//               listener: (context, state) {
//                 if (state.wasIncremented == true) {
//                   ScaffoldMessenger.of(context).showSnackBar(
//                     SnackBar(
//                       content: Text('Incremented!'),
//                       duration: Duration(milliseconds: 300),
//                     ),
//                   );
//                 } else if (state.wasIncremented == false) {
//                   ScaffoldMessenger.of(context).showSnackBar(
//                     SnackBar(
//                       content: Text('Decremented!'),
//                       duration: Duration(milliseconds: 300),
//                     ),
//                   );
//                 }
//               },
//               builder: (context, state) {
//                 if (state.counterValue < 0) {
//                   return Text(
//                     'BRR, NEGATIVE ' + state.counterValue.toString(),
//                     style: Theme.of(context).textTheme.headline4,
//                   );
//                 } else if (state.counterValue % 2 == 0) {
//                   return Text(
//                     'YAAAY ' + state.counterValue.toString(),
//                     style: Theme.of(context).textTheme.headline4,
//                   );
//                 } else if (state.counterValue == 5) {
//                   return Text(
//                     'HMM, NUMBER 5',
//                     style: Theme.of(context).textTheme.headline4,
//                   );
//                 } else
//                   return Text(
//                     state.counterValue.toString(),
//                     style: Theme.of(context).textTheme.headline4,
//                   );
//               },
//             ),
//             SizedBox(
//               height: 24,
//             ),
//             Row(
//               mainAxisAlignment: MainAxisAlignment.spaceEvenly,
//               children: [
//                 FloatingActionButton(
//                   heroTag: Text('${widget.title}'),
//                   onPressed: () {
//                     BlocProvider.of<CounterCubit>(context).decrement();
//                     // context.bloc<CounterCubit>().decrement();
//                   },
//                   tooltip: 'Decrement',
//                   child: Icon(Icons.remove),
//                 ),
//                 FloatingActionButton(
//                   heroTag: Text('${widget.title} #2'),
//                   onPressed: () {
//                     // BlocProvider.of<CounterCubit>(context).increment();
//                     context.bloc<CounterCubit>().increment();
//                   },
//                   tooltip: 'Increment',
//                   child: Icon(Icons.add),
//                 ),
//               ],
//             ),
//           ],
//         ),
//       ),
//     );
//   }
// }
