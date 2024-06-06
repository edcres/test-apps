// Events are actions that can happen in your app. They represent
//    user actions or other changes that might affect the state.
// All possible events.
abstract class CounterEvent {}

class CounterIncrement extends CounterEvent {}

class CounterDecrement extends CounterEvent {}
