package com.example.app

import com.example.app.data.response.ListStoryItem

object DataDummy {

    fun generateDummyStoryResponse(): List<ListStoryItem> {
        val items: MutableList<ListStoryItem> = arrayListOf()
        for (i in 0..100) {
            val quote = ListStoryItem(
                i.toString(),
                "photoUrl $i",
                "name $i",
                "desc $i",
                "createdAt $i",
                0.0,
                0.0
            )
            items.add(quote)
        }
        return items
    }
}