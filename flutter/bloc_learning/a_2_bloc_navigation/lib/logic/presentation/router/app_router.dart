import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'package:flutter_bloc_concepts/logic/presentation/screens/home_screen.dart';
import 'package:flutter_bloc_concepts/logic/presentation/screens/second_screen.dart';
import 'package:flutter_bloc_concepts/logic/presentation/screens/third_screen.dart';

class AppRouter {
  // return the generated screen
  Route onGenerateRoute(RouteSettings routeSettings) {
    switch (routeSettings.name) {
      case '/':
        return MaterialPageRoute(
            builder: (_) => HomeScreen(
                  title: 'HomeScreen',
                  color: Colors.blueAccent,
                ));
        break;
      case '/':
        return MaterialPageRoute(
            builder: (_) => SecondScreen(
                  title: 'SecondScreen',
                  color: Colors.redAccent,
                ));
        break;
      case '/':
        return MaterialPageRoute(
            builder: (_) => ThirdScreen(
                  title: 'ThirdScreen',
                  color: Colors.greenAccent,
                ));
        break;
      default:
    }
  }
}
