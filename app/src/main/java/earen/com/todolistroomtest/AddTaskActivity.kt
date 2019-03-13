package earen.com.todolistroomtest

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import earen.com.todolistroomtest.database.AppDataBase
import earen.com.todolistroomtest.database.Task
import kotlinx.android.synthetic.main.activity_add_task.*
import kotlinx.android.synthetic.main.layout_row_item.*
import java.util.*
import android.widget.RadioGroup




class AddTaskActivity : AppCompatActivity() {
    lateinit var appDataBase: AppDataBase
    // Constants for priority
    val PRIORITY_HIGH = 1
    val PRIORITY_MEDIUM = 2
    val PRIORITY_LOW = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        appDataBase = AppDataBase.getsInstance(this)!!
        saveButton.setOnClickListener(View.OnClickListener {
                val text = editTextTaskDescription.getText().toString().trim()
                val priority = getPriorityFromViews()
                val date = Date()
                val task = Task(text, priority, date)
                appDataBase.taskDao().insertTask(task)
                finish()
        })
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
