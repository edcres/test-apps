import 'package:flutter_bloc/flutter_bloc.dart';

class LoggerState {
  final List<String> logs;
  LoggerState(this.logs);
}

class LoggerBloc extends Cubit<LoggerState> {
  LoggerBloc() : super(LoggerState([]));

  void log(String message) {
    final newLogs = List<String>.from(state.logs)..add(message);
    emit(LoggerState(newLogs));
  }
}
