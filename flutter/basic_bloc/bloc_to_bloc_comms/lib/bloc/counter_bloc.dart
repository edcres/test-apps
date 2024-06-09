import 'package:flutter_bloc/flutter_bloc.dart';
import 'logger_bloc.dart';

class CounterEvent {}

class IncrementEvent extends CounterEvent {}

class CounterState {
  final int counter;
  CounterState(this.counter);
}

class CounterBloc extends Bloc<CounterEvent, CounterState> {
  final LoggerBloc loggerBloc;

  CounterBloc(this.loggerBloc) : super(CounterState(0)) {
    on<IncrementEvent>((event, emit) {
      final newState = CounterState(state.counter + 1);
      emit(newState);
      loggerBloc.log('Counter value changed to ${newState.counter}');
    });
  }
}
