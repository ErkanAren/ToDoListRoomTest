package earen.com.todolistroomtest.database

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

/*
    Entity(tableName = "task")
    Each Entity  class represents an entity in a table. Annotate your class declaration  to indicate that it's an entity. Specify the name of the table if you  want it to be different from the name of the class.
    PrimaryKey
    Every entity needs a primary key. To keep things simple, each Task acts as its own primary key.
    ColumnInfo(name = "updated_at")
    Specify the name of the column in the table if you want it to be different from the name of the member variable.
 */
@Entity(tableName = "task")
class Task(
    var description: String?, var priority: Int, @field:ColumnInfo(name = "updated_at")
    var updatedAt: Date?
) {



    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

}