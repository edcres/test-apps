import 'package:flutter/material.dart';
import 'package:cloud_firestore/cloud_firestore.dart';

class HomeScreen extends StatefulWidget {
  @override
  _HomeScreenState createState() => _HomeScreenState();
}

class _HomeScreenState extends State<HomeScreen> {
  final TextEditingController _controller = TextEditingController();
  bool _isSaved = false;
  String _displayText = "Loading...";

  @override
  void initState() {
    super.initState();
    _fetchText();
  }

  void _fetchText() async {
    DocumentSnapshot doc =
        await FirebaseFirestore.instance.collection('texts').doc('doc 1').get();
    setState(() {
      if (doc.exists && doc['text'] != null) {
        _displayText = doc['text'];
      } else {
        _displayText = "No text saved";
      }
    });
  }

  void _saveText() async {
    String text = _controller.text;
    if (text.isNotEmpty) {
      try {
        await FirebaseFirestore.instance.collection('texts').doc('doc 1').set({
          'text': text,
          'timestamp': FieldValue.serverTimestamp(),
        });
        setState(() {
          _isSaved = true;
          _displayText = text;
        });
        _controller.clear();
      } catch (e) {
        print('Error saving text: $e');
      }
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Firestore Example'),
      ),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          children: [
            TextField(
              controller: _controller,
              decoration: InputDecoration(
                labelText: 'Enter some text',
              ),
            ),
            SizedBox(height: 20),
            ElevatedButton(
              onPressed: _saveText,
              child: Text('Save Text'),
            ),
            SizedBox(height: 20),
            Text(
              _displayText,
              style: TextStyle(fontSize: 16),
            ),
            if (_isSaved)
              Padding(
                padding: const EdgeInsets.only(top: 20.0),
                child: Text(
                  'Text saved',
                  style: TextStyle(color: Colors.green, fontSize: 16),
                ),
              ),
          ],
        ),
      ),
    );
  }
}
