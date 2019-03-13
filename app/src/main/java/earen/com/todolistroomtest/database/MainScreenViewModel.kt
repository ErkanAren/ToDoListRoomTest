package earen.com.todolistroomtest.database

import android.arch.lifecycle.LiveData
import android.app.Application
import android.support.annotation.NonNull
import android.arch.lifecycle.AndroidViewModel
import android.util.Log


class MainScreenViewModel(application: Application) : AndroidViewModel(application) {
    val taskList: LiveData<List<Task>>

    init {
        val appDataBase = AppDataBase.getsInstance(this.getApplication())
        Log.i("View Model", " Retrieving data from database")
        taskList = appDataBase!!.taskDao().loadAllTask()

    }
}