import 'package:flutter_bloc/flutter_bloc.dart';
import 'data_event.dart';
import 'data_state.dart';

class DataBloc extends Bloc<DataEvent, DataState> {
  DataBloc() : super(InitialState()) {
    on<FetchDataEvent>(_onFetchData);
  }

  void _onFetchData(FetchDataEvent event, Emitter<DataState> emit) async {
    emit(LoadingState());
    try {
      final data = await _fetchData();
      emit(LoadedState(data));
    } catch (e) {
      emit(ErrorState('Failed to fetch data'));
    }
  }

  Future<List<String>> _fetchData() async {
    await Future.delayed(Duration(seconds: 2));
    if (DateTime.now().second % 2 == 0) {
      return ['Data 1', 'Data 2', 'Data 3'];
    } else {
      throw Exception('Error fetching data');
    }
  }
}
