package io.github.wertylop5.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class InfoRepository(private val dao: InfoDao) {
    private val TAG = InfoRepository::class.java.simpleName

    var infoItems: LiveData<MutableList<Info>> = dao.getAll()
        private set

    suspend fun insertInfo(info: Info) {
        dao.insertInfoItem(info)
    }

    suspend fun insertInfoItems(items: MutableList<Info>) {
        Log.d(TAG, items.toString())
        val rows = dao.insertInfoItems(items)
        Log.d(TAG, rows.toString())
    }

    fun getInfoFromEntryId(id: Int) {
        infoItems = dao.getInfoFromEntryId(id)
    }
}