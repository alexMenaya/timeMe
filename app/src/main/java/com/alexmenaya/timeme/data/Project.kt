package com.alexmenaya.timeme.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.alexmenaya.timeme.singletons.DataController
import kotlin.reflect.KMutableProperty
import kotlin.reflect.full.memberProperties

@Entity
data class Project(
    @PrimaryKey var uid: String,
    // DB Management
    @ColumnInfo(name = "date_created") var date_created: Int = 0,
    @ColumnInfo(name = "date_updated") var date_updated: Int = 0,
    @ColumnInfo(name = "is_deleted") var is_deleted: Boolean = false,
    // Project fields
    @ColumnInfo(name = "id_owner") var id_owner: String,
    @ColumnInfo(name = "project_name") var project_name: String,
    @ColumnInfo(name = "color") var color: String
): BaseEntity() {

    constructor(uid: String): this(uid,
        date_created = 0,
        date_updated = 0,
        is_deleted = false,
        id_owner = "",
        project_name = "",
        color = "000000"
    )

    fun updateMap(
        mapIn: Map<*, *>
    ) {
        val properties = this::class.memberProperties.filter { kProp ->
            (mapIn.containsKey(kProp.name))
        }
        for (property in properties) {
            if (property !is KMutableProperty<*>) continue
            setProperty(property, mapIn)
        }
        DataController.appDatabase.projectDao().update(this)
    }

    companion object {

        fun fromMap(
            mapIn: Map<*, *>
        ): Project {
            val instance = Project("tempProjectId")
            val properties = this::class.memberProperties.filter{ kProp ->
                (mapIn.containsKey(kProp.name))
            }
            for (property in properties) {
                if (property !is KMutableProperty<*>) continue
                setProperty(property, mapIn)
            }
            return instance
        }

        fun setProperty(
            property: KMutableProperty<*>,
            mapIn: Map<*, *>
        ) {
            when (property.returnType.toString()) {
                "kotlin.Int" -> {
                    val intVal = (mapIn[property.name] as Double).toInt()
                    property.setter.call(this, intVal)
                }

                "kotlin.Float" -> {
                    val floatValue = (mapIn[property.name] as Double).toFloat()
                    property.setter.call(this, floatValue)
                }

                "kotlin.String?" -> {
                    property.setter.call(this, mapIn[property.name])
                }

                "kotlin.Boolean" -> {
                    val intVal = (mapIn[property.name] as Double).toInt()
                    property.setter.call(this, (intVal == 1))
                }

                else -> {
                    property.setter.call(this, mapIn[property.name])
                }
            }
        }
    }
}
