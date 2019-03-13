package earen.com.todolistroomtest.database

import java.util.concurrent.Executor
import java.util.concurrent.Executors

class AppExecutor private constructor(private val diskIO: Executor) {

    fun diskIO(): Executor {

        return diskIO

    }

    companion object {

        //this is for singleton instantiation

        private val LOCK = Any()
        private var sInstance: AppExecutor? = null
        val instance: AppExecutor
            get() {

                if (sInstance == null) {

                    synchronized(LOCK) {

                        sInstance = AppExecutor(Executors.newSingleThreadExecutor())
                    }

                }

                return sInstance!!

            }
    }

}