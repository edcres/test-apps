import 'package:bloc/bloc.dart';
import 'counter_event.dart';
import 'counter_state.dart';

class CounterBloc extends Bloc<CounterEvent, CounterState> {
  CounterBloc() : super(CounterState(0)) {
    on<CounterIncrement>((event, emit) {
      emit(CounterState(state.counter + 1));
    });

    on<CounterDecrement>((event, emit) {
      emit(CounterState(state.counter - 1));
    });
  }
}
