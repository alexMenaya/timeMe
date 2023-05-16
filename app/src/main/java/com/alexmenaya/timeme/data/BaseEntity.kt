package com.alexmenaya.timeme.data

import kotlin.reflect.full.memberProperties

open class BaseEntity {

    private fun fieldNames(): List<String> {
        val properties = this::class.memberProperties
        return properties.map { it.name }
    }

    private fun allValues(): MutableMap<String, String> {
        val mapOut: MutableMap<String, String> = mutableMapOf()
        val properties = this.fieldNames()
        for (field in properties) {
            this::class.memberProperties.filter { it.name == field }.forEach { prop ->
                val value = prop.getter.call(this)
                if (value != null) {
                    mapOut[field] = value.toString()
                }
            }
        }
        return mapOut
    }

    companion object {
        fun fromMap(
            mapIn: Map<String, String>
        ): BaseEntity? {
            return null
        }
    }
}