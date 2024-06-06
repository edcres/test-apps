import 'package:bloc/bloc.dart';
import 'counter_event.dart';
import 'counter_state.dart';

// Manages the logic of transitioning from one
//    state to another in response to events.

class CounterBloc extends Bloc<CounterEvent, CounterState> {
  // The counter starts at 0.
  CounterBloc() : super(CounterState(0)) {
    // on<CounterIncrement> and on<CounterDecrement> methods listen for
    //    their respective events and update the state accordingly by
    //    emitting a new CounterState
    on<CounterIncrement>((event, emit) {
      emit(CounterState(state.counter + 1));
    });

    on<CounterDecrement>((event, emit) {
      emit(CounterState(state.counter - 1));
    });
  }
}
