import 'package:flutter/material.dart';

// 1. Unified Route Handling: All routes are now handled in onGenerateRoute, making it easier to manage routes from a single place.
// 2. Removed Redundant Route Declaration: The initial named route / is now handled within onGenerateRoute.
// 3. Direct Argument Passing: Arguments are handled directly using the settings.arguments property, maintaining a clean and consistent approach.

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Navigation Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      initialRoute: '/',
      // Here we use 'onGenerateRoute' as opposed to the 'routes' map
      onGenerateRoute: (settings) {
        switch (settings.name) {
          case '/':
            return MaterialPageRoute(builder: (context) => FirstScreen());
          case '/second':
            final args = settings.arguments as ScreenArguments;
            return MaterialPageRoute(
                builder: (context) => SecondScreen(data: args.data));
          default:
            assert(false, 'Need to implement ${settings.name}');
            return null;
        }
      },
    );
  }
}

class ScreenArguments {
  final String data;

  ScreenArguments(this.data);
}

class FirstScreen extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('First Screen'),
      ),
      body: Center(
        child: ElevatedButton(
          onPressed: () {
            Navigator.pushNamed(
              context,
              '/second',
              arguments: ScreenArguments('Hello from First Screen!'),
            );
          },
          child: Text('Go to Second Screen'),
        ),
      ),
    );
  }
}

class SecondScreen extends StatelessWidget {
  final String data;

  SecondScreen({required this.data});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Second Screen'),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            Text(data),
            SizedBox(height: 20),
            ElevatedButton(
              onPressed: () {
                Navigator.pop(context);
              },
              child: Text('Go back to First Screen'),
            ),
          ],
        ),
      ),
    );
  }
}
