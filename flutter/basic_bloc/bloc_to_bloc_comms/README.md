# bloc_to_bloc_comms

____________________________________________________________

The BLoC-to-BLoC communication is achieved by:

    -Passing an instance of LoggerBloc to CounterBloc.
    -Having CounterBloc call LoggerBloc's log method to log messages whenever the counter value changes.

____________________________________________________________

- CounterBloc: Manages the counter state and communicates state changes to LoggerBloc.

- LoggerBloc: Logs messages received from CounterBloc.

- UI: Displays the counter value and log messages. The Increment button triggers the counter increment and logs the change.

____________________________________________________________

How Communication Works

    Initialization:
        LoggerBloc is initialized first.
        CounterBloc is initialized and provided with an instance of LoggerBloc.

    Event Handling in CounterBloc:
        When the IncrementEvent is added to CounterBloc, it triggers the on<IncrementEvent> method.
        Inside this method, the counter value is incremented, and the new state is emitted.
        The LoggerBloc's log method is called with a message that includes the new counter value.

    Logging:
        LoggerBloc receives the log message, updates its state by adding the new message to the list of logs, and emits the new state.

UI Components

    Counter Display:
        BlocBuilder<CounterBloc, CounterState> rebuilds the UI to display the updated counter value whenever the state changes.

    Increment Button:
        The button's onPressed callback adds an IncrementEvent to the CounterBloc.

    Log Display:
        BlocBuilder<LoggerBloc, LoggerState> rebuilds the list of logs whenever a new log message is added to the state.