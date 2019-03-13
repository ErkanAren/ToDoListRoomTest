package earen.com.todolistroomtest

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import earen.com.todolistroomtest.database.AppDataBase
import earen.com.todolistroomtest.database.Task
import kotlinx.android.synthetic.main.activity_add_task.*
import java.util.*

import android.util.Log
import earen.com.todolistroomtest.database.AppExecutor


class AddTaskActivity : AppCompatActivity() {
    lateinit var appDataBase: AppDataBase
    // Constants for priority
    val PRIORITY_HIGH = 1
    val PRIORITY_MEDIUM = 2
    val PRIORITY_LOW = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        val intent = intent
        appDataBase = AppDataBase.getsInstance(this)!!

        if (intent != null && intent.hasExtra("id")) {

            saveButton.setText("Update")

            val task = appDataBase.taskDao().getTaskById(intent.getIntExtra("id", 0))


            Log.i("DetailActivity ", " Reading Database")

            task.observe(this, android.arch.lifecycle.Observer<Task> {
                    editTextTaskDescription.setText(it!!.description)
                    setPriority(it.priority)
            })
        }

        Log.i("DetailActivity ", " Reading DataBase")


        saveButton.setOnClickListener(View.OnClickListener {
                val text = editTextTaskDescription.getText().toString().trim()
                val priority = getPriorityFromViews()
                val date = Date()
                val task = Task(text, priority, date)
                /*
                he onclickListener is where we add a new Task to the database. Firstly we create a new task object passing the user data as its parameters that is the text, priority and date.
                Now we implement the AppExecutor class with a new Runnable object.
                In the run method of Runnable object, we include our database code
                */
                    //we are using app executor because we can't access to our database in the main thread
            AppExecutor.instance.diskIO().execute(Runnable {
                if (intent != null && intent.hasExtra("id")) {
                    task.id =(intent.getIntExtra("id", 0));
                    appDataBase.taskDao().updateTask(task);
                } else {
                    appDataBase.taskDao().insertTask(task);
                }
                finish()
            })

        })
    }

    private fun setPriority(priority: Int) {
        when (priority){
            1 -> radButton1.isChecked = true
            2->radButton2.isChecked = true
            3->radButton3.isChecked = true
        }

    }

    /**
     * getPriority is called whenever the selected priority needs to be retrieved
     */
    fun getPriorityFromViews(): Int {
        var priority = 1
        val checkedId = radioGroup.checkedRadioButtonId
        when (checkedId) {
            radButton1.id -> priority = PRIORITY_HIGH
            radButton2.id -> priority = PRIORITY_MEDIUM
            radButton3.id -> priority = PRIORITY_LOW
        }
        return priority
    }

}
