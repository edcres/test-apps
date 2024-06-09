import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'bloc/counter_bloc.dart';
import 'bloc/logger_bloc.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MultiBlocProvider(
      providers: [
        BlocProvider<LoggerBloc>(create: (context) => LoggerBloc()),
        BlocProvider<CounterBloc>(
          create: (context) => CounterBloc(context.read<LoggerBloc>()),
        ),
      ],
      child: MaterialApp(
        home: HomeScreen(),
      ),
    );
  }
}

class HomeScreen extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text('BLoC to BLoC Communication')),
      body: Column(
        children: [
          BlocBuilder<CounterBloc, CounterState>(
            builder: (context, state) {
              return Text('Counter: ${state.counter}',
                  style: TextStyle(fontSize: 24));
            },
          ),
          ElevatedButton(
            onPressed: () {
              context.read<CounterBloc>().add(IncrementEvent());
            },
            child: Text('Increment'),
          ),
          BlocBuilder<LoggerBloc, LoggerState>(
            builder: (context, state) {
              return Expanded(
                child: ListView.builder(
                  itemCount: state.logs.length,
                  itemBuilder: (context, index) {
                    return ListTile(
                      title: Text(state.logs[index]),
                    );
                  },
                ),
              );
            },
          ),
        ],
      ),
    );
  }
}
