package earen.com.todolistroomtest

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import earen.com.todolistroomtest.database.AppDataBase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var toDoListAdapter:ToDoListAdapter
    var appDataBase:AppDataBase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val layoutManager = LinearLayoutManager(this)

        recycler_view_main.setLayoutManager(layoutManager)

        toDoListAdapter = ToDoListAdapter(this)

        recycler_view_main.setAdapter(toDoListAdapter)

        appDataBase = AppDataBase.getsInstance(this)

        fab.setOnClickListener(View.OnClickListener{
            val intent = Intent(this,AddTaskActivity::class.java)
            startActivity(intent)
        })
    }

    override fun onResume() {
        super.onResume()
        toDoListAdapter.setTasks(appDataBase!!.taskDao().loadAllTask())
    }
}
