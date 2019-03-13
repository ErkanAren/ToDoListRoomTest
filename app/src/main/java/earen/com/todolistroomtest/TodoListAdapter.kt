package earen.com.todolistroomtest

import android.content.Context
import android.widget.TextView
import earen.com.todolistroomtest.database.Task
import android.support.v4.content.ContextCompat
import android.graphics.drawable.GradientDrawable
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import earen.com.todolistroomtest.database.AppExecutor
import java.text.SimpleDateFormat
import java.util.*

/*

    The TodoListAdapter is a custom adapter that populates the RecyclerView with the data model from the database.
    The Adapter class Constructor takes a Context as a paramenter. This context is to enabled the adapter determine the class where the adpter Object is being created. E.g The TodoListAdapter is instantiated in the MainActivity.
    In the OncreateViewHolder Method, we inflate the custom row view item layout we are using to display each entity and then return a new viewHolder object with this view as its parameter.
    In the OnbindViewHolder, we get each entity an set the atrributes to text the getPriority method is to return a color based on the task priority
    getCount method returns the size of the list
    the setTask methods provides the adapter with data from the database and then notify adapter for change

 */
class ToDoListAdapter// the adapter constructor
    (private val mContext: Context) : RecyclerView.Adapter<ToDoListAdapter.TaskViewHolder>() {

    // Class variables for the List that holds task data and the Context
    private var mTaskEntries: List<Task>? = null

    // Date formatter
    private val dateFormat = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        // Inflate the task_layout to a view

        val view = LayoutInflater.from(mContext)
            .inflate(R.layout.layout_row_item, parent, false)

        return TaskViewHolder(view)

    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {

        // Determine the values of the wanted data

        val taskEntry = mTaskEntries!![position]
        val description = taskEntry.description
        val priority = taskEntry.priority
        val updatedAt = dateFormat.format(taskEntry.updatedAt)
        //        Toast.makeText(mContext,description,Toast.LENGTH_LONG).show();
        //Set values
        holder.taskDescriptionView.text = description
        holder.updatedAtView.text = updatedAt
        // Programmatically set the text and color for the priority //TextView
        val priorityString = "" + priority // converts int to String
        holder.priorityView.text = priorityString
        val priorityCircle = holder.priorityView.background as GradientDrawable
        // Get the appropriate background color based on the priority
        val priorityColor = getPriorityColor(priority)
        priorityCircle.setColor(priorityColor)

    }

    private fun getPriorityColor(priority: Int): Int {

        var priorityColor = 0
        when (priority) {
            1 -> priorityColor = ContextCompat.getColor(mContext, R.color.materialRed)
            2 -> priorityColor = ContextCompat.getColor(mContext, R.color.materialOrange)
            3 -> priorityColor = ContextCompat.getColor(mContext, R.color.materialYellow)
            else -> {
            }
        }
        return priorityColor
    }

    override fun getItemCount(): Int {
        return if (mTaskEntries == null) {
            0
        } else mTaskEntries!!.size

    }

    /**
     *
     * When data changes, this method updates the list of taskEntries
     * and notifies the adapter to use the new values on it
     */
    fun setTasks(taskEntries: List<Task>) {
        mTaskEntries = taskEntries
        notifyDataSetChanged()
    }

    fun getTasks(): List<Task>? {
        return mTaskEntries
    }


    // Inner class for creating ViewHolders
    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Class variables for the task description and priority TextViews
        var taskDescriptionView: TextView
        var updatedAtView: TextView
        var priorityView: TextView

        init {
            taskDescriptionView = itemView.findViewById(R.id.taskDescription)
            updatedAtView = itemView.findViewById(R.id.taskUpdatedAt)
            priorityView = itemView.findViewById(R.id.priorityTextView)
        }
    }

    companion object {

        // Constant for date format<
        private val DATE_FORMAT = "dd/MM/yyy"
    }
}