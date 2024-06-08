# bloc_loading_states

What the App Does
    1. Initial State: When the app starts, it is in the InitialState.
    2. Fetching Data: When the app starts, it automatically triggers the FetchDataEvent, causing the BLoC to enter the LoadingState.
    3. Loading State: While data is being fetched, the UI shows a CircularProgressIndicator to indicate loading.
    4. Loaded State: If data fetching is successful, the BLoC transitions to the LoadedState with the fetched data, and the UI displays the    data in a list.
    5. Error State: If an error occurs during data fetching, the BLoC transitions to the ErrorState, and the UI displays an error message.
