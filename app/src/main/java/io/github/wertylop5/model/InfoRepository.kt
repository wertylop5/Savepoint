package io.github.wertylop5.model

import android.util.Log
import androidx.lifecycle.LiveData

class InfoRepository(private val dao: InfoDao) {
    private val TAG = InfoRepository::class.java.simpleName

    var infoItems: LiveData<List<Info>> = dao.getAll()
        private set

    suspend fun insertInfo(info: Info) {
        dao.insertInfoItem(info)
    }

    suspend fun insertInfoItems(items: List<Info>) {
        Log.d(TAG, items.toString())
        dao.insertInfoItems(items)
    }

    fun getInfoFromEntryId(id: Int): LiveData<List<Info>> {
        infoItems = dao.getInfoFromEntryId(id)
        return infoItems
    }

    fun clearInfoCache() {

    }
}