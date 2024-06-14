import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:cloud_firestore/cloud_firestore.dart';
import 'text_bloc.dart';
import 'text_event.dart';
import 'text_state.dart';

class HomeScreen extends StatelessWidget {
  final TextEditingController _controller = TextEditingController();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Firestore BLoC Example'),
      ),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          children: [
            TextField(
              controller: _controller,
              decoration: InputDecoration(
                labelText: 'Enter some text',
              ),
            ),
            SizedBox(height: 20),
            ElevatedButton(
              onPressed: () {
                final text = _controller.text;
                if (text.isNotEmpty) {
                  context.read<TextBloc>().add(SaveText(text));
                  _controller.clear();
                }
              },
              child: Text('Save Text'),
            ),
            SizedBox(height: 20),
            BlocBuilder<TextBloc, TextState>(
              builder: (context, state) {
                if (state is TextLoading) {
                  return CircularProgressIndicator();
                } else if (state is TextLoaded) {
                  return Text(state.text, style: TextStyle(fontSize: 16));
                } else if (state is TextSaved) {
                  return Text('Text saved',
                      style: TextStyle(color: Colors.green, fontSize: 16));
                } else if (state is TextError) {
                  return Text(state.message,
                      style: TextStyle(color: Colors.red, fontSize: 16));
                } else {
                  return Container();
                }
              },
            ),
          ],
        ),
      ),
    );
  }
}
