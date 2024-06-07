import 'dart:async';

abstract class CounterEvent {}

class IncrementEvent extends CounterEvent {}

class DecrementEvent extends CounterEvent {}

class CounterState {
  final int counter;

  CounterState(this.counter);
}

class CounterBloc {
  int _counter = 0;

  final _stateController = StreamController<CounterState>();
  final _eventController = StreamController<CounterEvent>();

  // Output stream
  Stream<CounterState> get stateStream => _stateController.stream;

  // Input sink
  StreamSink<CounterEvent> get eventSink => _eventController.sink;

  CounterBloc() {
    _eventController.stream.listen(_mapEventToState);
  }

  // _mapEventToState method updates the counter based on the event and adds
  //    the new state to the _stateController.
  void _mapEventToState(CounterEvent event) {
    if (event is IncrementEvent) {
      _counter++;
    } else if (event is DecrementEvent) {
      _counter--;
    }
    _stateController.sink.add(CounterState(_counter));
  }

  void dispose() {
    _stateController.close();
    _eventController.close();
  }
}
