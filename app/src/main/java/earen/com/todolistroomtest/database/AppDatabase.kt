package earen.com.todolistroomtest.database

import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.arch.persistence.room.Database
import android.content.Context
import android.util.Log


@Database(entities = [Task::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class AppDataBase : RoomDatabase() {

    abstract fun taskDao(): TaskDataAccessObject

    companion object {

        val LOG_TAG = AppDataBase::class.java.simpleName
        val LOCK = Any()
        val DATABASE_NAME = "todo_list"
        private var sInstance: AppDataBase? = null

        fun getsInstance(context: Context): AppDataBase? {
            if (sInstance == null) {
                synchronized(LOCK) {
                    Log.d(LOG_TAG, "creating new database")

                    sInstance = Room.databaseBuilder(
                        context.getApplicationContext(),
                        AppDataBase::class.java, AppDataBase.DATABASE_NAME
                    )
                        .build()
                }
            }

            Log.d(LOG_TAG, "getting the database instance")

            return sInstance

        }
    }

}