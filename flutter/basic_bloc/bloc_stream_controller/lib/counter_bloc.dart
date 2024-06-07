import 'dart:async';

class CounterBloc {
  int _counter = 0;

  final _counterController = StreamController<int>();

  // Output stream
  Stream<int> get counterStream => _counterController.stream;

  // Input sink
  void increment() {
    _counter++;
    _counterController.sink.add(_counter);
  }

  void decrement() {
    _counter--;
    _counterController.sink.add(_counter);
  }

  void dispose() {
    _counterController.close();
  }
}
