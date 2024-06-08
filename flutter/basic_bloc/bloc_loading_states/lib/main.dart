import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'bloc/data_bloc.dart';
import 'bloc/data_event.dart';
import 'bloc/data_state.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter BLoC Example',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: BlocProvider(
        create: (context) => DataBloc()..add(FetchDataEvent()),
        child: MyHomePage(),
      ),
    );
  }
}

class MyHomePage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Flutter BLoC Example'),
      ),
      body: BlocBuilder<DataBloc, DataState>(
        builder: (context, state) {
          if (state is LoadingState) {
            return Center(
              child: CircularProgressIndicator(),
            );
          } else if (state is LoadedState) {
            return ListView.builder(
              itemCount: state.data.length,
              itemBuilder: (context, index) {
                return ListTile(
                  title: Text(state.data[index]),
                );
              },
            );
          } else if (state is ErrorState) {
            return Center(
              child: Text('Error: ${state.message}'),
            );
          }
          return Center(
            child: Text('Press button to fetch data'),
          );
        },
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: () {
          context.read<DataBloc>().add(FetchDataEvent());
        },
        child: Icon(Icons.refresh),
      ),
    );
  }
}
