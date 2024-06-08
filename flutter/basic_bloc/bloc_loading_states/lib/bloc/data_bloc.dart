import 'package:flutter_bloc/flutter_bloc.dart';
import 'data_event.dart';
import 'data_state.dart';

class DataBloc extends Bloc<DataEvent, DataState> {
  DataBloc() : super(InitialState()) {
    // This tells the BLoC that whenever a FetchDataEvent is added to the BLoC,
    //   it should handle this event using the specified event handler (_onFetchData).
    on<FetchDataEvent>(_onFetchData);
  }

// This function is responsible for processing the event and emitting
//  new states based on the logic defined within it.
  void _onFetchData(FetchDataEvent event, Emitter<DataState> emit) async {
    emit(LoadingState());
    try {
      final data = await _fetchData();
      emit(LoadedState(data));
    } catch (e) {
      emit(ErrorState('Failed to fetch data'));
    }
  }

  // Fetches fake data.
  Future<List<String>> _fetchData() async {
    await Future.delayed(Duration(seconds: 2));
    if (DateTime.now().second % 2 == 0) {
      return ['Data 1', 'Data 2', 'Data 3'];
    } else {
      throw Exception('Error fetching data');
    }
  }
}
