package io.github.wertylop5

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CreateNoteViewModel: ViewModel() {
    private val TAG: String = CreateNoteViewModel::class.java.name

    val listOfInfo: MutableLiveData<MutableList<Info>> = MutableLiveData<MutableList<Info>>()

    init {
        listOfInfo.value = ArrayList()
    }

    fun insert(i: Info) {
//        listOfInfo.value.let {
//            Log.d(TAG, "inserting info into viewmodel")
//
//            it?.add(i)
//            Log.d(TAG, it?.size?.toString())
//        }

        //listOfInfoInternal.add(i)
        //TODO how to update livedata list
        listOfInfo.value?.add(i)
        listOfInfo.value = listOfInfo.value

        Log.d(TAG, "inserting info into viewmodel")
        Log.d(TAG, listOfInfo.value?.size.toString())
    }
}