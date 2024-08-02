import 'package:bloc/bloc.dart';
import 'package:cloud_firestore/cloud_firestore.dart';
import 'text_event.dart';
import 'text_state.dart';

class TextBloc extends Bloc<TextEvent, TextState> {
  TextBloc() : super(TextInitial()) {
    on<LoadText>(_onLoadText);
    on<SaveText>(_onSaveText);
  }

  Future<void> _onLoadText(LoadText event, Emitter<TextState> emit) async {
    emit(TextLoading());
    try {
      DocumentSnapshot doc = await FirebaseFirestore.instance
          .collection('texts')
          .doc('doc 1')
          .get();
      if (doc.exists && doc['text'] != null) {
        emit(TextLoaded(doc['text']));
      } else {
        emit(TextLoaded('No text saved'));
      }
    } catch (e) {
      emit(TextError('Failed to load text'));
    }
  }

  Future<void> _onSaveText(SaveText event, Emitter<TextState> emit) async {
    try {
      await FirebaseFirestore.instance.collection('texts').doc('doc 1').set({
        'text': event.text,
        'timestamp': FieldValue.serverTimestamp(),
      });
      emit(TextSaved());
      add(LoadText());
    } catch (e) {
      emit(TextError('Failed to save text'));
    }
  }
}
