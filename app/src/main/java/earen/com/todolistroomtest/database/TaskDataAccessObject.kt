package earen.com.todolistroomtest.database

import android.arch.persistence.room.*

/*
    Create a new Interface and call it TaskDataAccessObject.
    Annotate the class with DAO to identify it as a DAO class for Room.
    Declare a method to insert one task: void insertTask(Task task);
    Annotate the method with Insert. You don't have to provide any SQL! (There are also Delete and Update annotations for deleting and updating a row)
    Declare a method to delete a task: void deleteTask(Task task);
    Create a method to get all the task: loadAllTask();.
    Have the method return a List of Task.
    List<Task> loadAllTask();
    Annotate the method with the SQL query:
    Query("SELECT * FROM task ORDER BY priority")
 */
@Dao
interface TaskDataAccessObject {

    @Query("SELECT * FROM task ORDER BY priority")
    fun loadAllTask(): List<Task>  // returns a list of task object

    @Insert
    fun insertTask(task: Task)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateTask(task: Task)

    @Delete
    fun deleteTask(task: Task)

}