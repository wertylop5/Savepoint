package io.github.wertylop5.model

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.launch

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
                repository.getInfoFromEntryId(-1)
                listOfInfo = repository.infoItems
                false
            }
            else -> {
                repository.getInfoFromEntryId(entryId)
                listOfInfo = repository.infoItems
                true
            }
        }
    }

    fun insert(i: Info) {
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
