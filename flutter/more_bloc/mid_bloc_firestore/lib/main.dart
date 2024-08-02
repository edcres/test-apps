import 'package:basic_bloc_firestore/firebase_options.dart';
import 'package:basic_bloc_firestore/text_event.dart';
import 'package:flutter/material.dart';
import 'package:firebase_core/firebase_core.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'home_screen.dart';
import 'text_bloc.dart';

void main() async {
  WidgetsFlutterBinding.ensureInitialized();
  await Firebase.initializeApp(
    options: DefaultFirebaseOptions.currentPlatform,
  );
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: BlocProvider(
        create: (context) => TextBloc()..add(LoadText()),
        child: HomeScreen(),
      ),
    );
  }
}
