import 'package:bloc/bloc.dart';
import 'package:cloud_firestore/cloud_firestore.dart';
import 'text_event.dart';
import 'text_state.dart';

class TextBloc extends Bloc<TextEvent, TextState> {
  final FirebaseFirestore firestore;

  TextBloc(this.firestore) : super(TextInitial()) {
    on<LoadText>((event, emit) async {
      emit(TextLoading());
      try {
        DocumentSnapshot doc =
            await firestore.collection('texts').doc('doc 1').get();
        if (doc.exists && doc['text'] != null) {
          emit(TextLoaded(doc['text']));
        } else {
          emit(TextLoaded("No text saved"));
        }
      } catch (e) {
        emit(TextError("Failed to load text"));
      }
    });

    on<SaveText>((event, emit) async {
      try {
        await firestore.collection('texts').doc('doc 1').set({
          'text': event.text,
          'timestamp': FieldValue.serverTimestamp(),
        });
        emit(TextSaved());
        add(LoadText()); // Reload the text after saving
      } catch (e) {
        emit(TextError("Failed to save text"));
      }
    });
  }
}
