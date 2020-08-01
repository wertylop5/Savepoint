package io.github.wertylop5.model

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.launch

//TODO: store a list of info in the db already and a separate list of temp info's being created
class CreateEntryViewModel(application: Application): AndroidViewModel(application) {
    private val TAG: String = CreateEntryViewModel::class.java.name

    private val repository: InfoRepository
    var listOfInfo: LiveData<List<Info>>
        private set

    var listOfInfoToAdd: MutableList<Info> = ArrayList()

    init {
        val infoDao = AppDatabase.getInstance(application, viewModelScope).infoDao()
        repository = InfoRepository(infoDao)
        listOfInfo = repository.infoItems
    }

    fun insert(i: Info) = viewModelScope.launch {
//        listOfInfo.value.let {getInfoFromEntryId
//            Log.d(TAG, "inserting info into viewmodel")
//
//            it?.add(i)
//            Log.d(TAG, it?.size?.toString())
//        }

        //listOfInfoInternal.add(i)
        //TODO how to update livedata list
//        listOfInfo.value?.add(i)
//        listOfInfo.value = listOfInfo.value
//
//        Log.d(TAG, "inserting info into viewmodel")
//        Log.d(TAG, listOfInfo.value?.size.toString())
        repository.insertInfo(i)
    }

    fun insertCreatedInfo() = viewModelScope.launch {
        repository.insertInfoItems(listOfInfoToAdd)
        listOfInfoToAdd.clear()
    }

    fun getInfoFromEntryId(entryId: Int) {
        /*listOfInfo = */repository.getInfoFromEntryId(entryId)
    }

    fun addInfoToCreate(i: Info) {
        listOfInfoToAdd.add(i)
    }
}
