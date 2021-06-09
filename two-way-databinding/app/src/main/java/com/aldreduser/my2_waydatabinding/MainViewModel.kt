package com.aldreduser.my2_waydatabinding

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    val textDisplayed = MutableLiveData<String>()

}

/*
React to changes in the data (like saving data to memory):
- uses @Bindable and observable class

In order to react to changes in the backing data, you can make your layout variable an
implementation of Observable, usually BaseObservable, and use a @Bindable annotation,
as shown in the following code snippet:

class LoginViewModel : BaseObservable {
    // val data: Model = ...

    @Bindable
    fun getRememberMe(): Boolean {
        return data.rememberMe
    }

    fun setRememberMe(value: Boolean) {
        // Avoids infinite loops.
        if (data.rememberMe != value) {
            data.rememberMe = value

            // React to the change.
            saveData()

            // Notify observers of a new value.
            notifyPropertyChanged(BR.remember_me)
        }
    }
}


https://developer.android.com/topic/libraries/data-binding/two-way
 */