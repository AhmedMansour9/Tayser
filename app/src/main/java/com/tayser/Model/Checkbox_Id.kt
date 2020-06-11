package com.chicchicken.Model

class Checkbox_Id {
    private var Id: Int? = null
    private var Title: String? = null

    fun getId(): Int? {
        return Id
    }

    fun setId(id: Int?) {
        Id = id
    }

    fun getTitle(): String? {
        return Title
    }

    fun setTitle(title: String?) {
        Title = title
    }
}