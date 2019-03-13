package earen.com.todolistroomtest

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import earen.com.todolistroomtest.database.AppDataBase
import kotlinx.android.synthetic.main.activity_main.*
import earen.com.todolistroomtest.database.AppExecutor

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper

import android.arch.lifecycle.Observer

import earen.com.todolistroomtest.database.MainScreenViewModel
import android.arch.lifecycle.ViewModelProviders






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

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {

                return false // We return false because we are not going to use it

            }

            // Called when a user swipes left or right on a ViewHolder
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {

                //  implement swipe to delete

                //get item position
                val position = viewHolder.adapterPosition
                // get list of Task
                val tasks = toDoListAdapter.getTasks()
                AppExecutor.instance.diskIO().execute(Runnable {
                 appDataBase!!.taskDao().deleteTask(tasks!!.get(position)) })
                //get all tasks and refresh recyclerView
                //getTasks()
            }

        }).attachToRecyclerView(recycler_view_main)

        getTasks()
    }

    override fun onResume() {
        super.onResume()
    }

   /* private fun getTasks() {
        AppExecutor.instance.diskIO().execute(Runnable {
            val tasks = appDataBase!!.taskDao().loadAllTask()
            runOnUiThread { toDoListAdapter.setTasks(tasks) }
        })
    }*/


    private fun getTasks() {

        val viewModel = ViewModelProviders.of(this).get(MainScreenViewModel::class.java)

        viewModel.taskList.observe(this@MainActivity,
            Observer { tasks -> toDoListAdapter.setTasks(tasks!!) })
    }


}
