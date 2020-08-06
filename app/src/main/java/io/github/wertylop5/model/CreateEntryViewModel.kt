package io.github.wertylop5.model

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.launch

//TODO: store a list of info in the db already and a separate list of temp info's being created
class CreateEntryViewModel(application: Application): AndroidViewModel(application) {
    companion object {
        val REFRESH_NO_EXISTING_ENTRY = -1
    }

    private val TAG: String = CreateEntryViewModel::class.java.name

    private val repository: InfoRepository
    var listOfInfo: LiveData<MutableList<Info>>
        private set
    var shouldDisplayInfoFromDb = false
        private set

    val listOfInfoToAdd: MutableList<Info> = ArrayList()

    init {
        val infoDao = AppDatabase.getInstance(application, viewModelScope).infoDao()
        repository = InfoRepository(infoDao)

        listOfInfo = repository.infoItems
    }

    fun refreshData(entryId: Int = REFRESH_NO_EXISTING_ENTRY) {
        shouldDisplayInfoFromDb = when(entryId) {
            REFRESH_NO_EXISTING_ENTRY -> {
                false
            }
            else -> {
                repository.getInfoFromEntryId(entryId)
                listOfInfo = repository.infoItems
                true
            }
        }
    }

    fun insert(i: Info)/* = viewModelScope.launch*/ {
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
        listOfInfoToAdd.add(i)
    }

    fun getInfoFromEntryId(entryId: Int) {
        repository.getInfoFromEntryId(entryId)
        //listOfInfo.value = repository.infoItems.value ?: ArrayList()
        listOfInfo = repository.infoItems
    }

    /**
     * Flushes all info elems not already in the database
     * */
    fun clearTempInfo() {
        listOfInfoToAdd.clear()
    }
}
