part of 'counter_cubit.dart';

// This class will be the blueprint for all the possible states emitted by the cubit
class CounterState {
  int counterValue;
  bool wasIncremented;

  CounterState({
    required this.counterValue,
    // @required this.counterValue,
    this.wasIncremented = false,
  });
}
