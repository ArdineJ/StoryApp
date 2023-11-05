package com.ardine.storyapp

import com.ardine.storyapp.data.response.ListStoryItem
import com.ardine.storyapp.data.response.LoginResponse
import com.ardine.storyapp.data.response.LoginResult
import com.ardine.storyapp.data.response.RegisterResponse

object DataDummy {
    fun generateDummyStoryResponse(): List<ListStoryItem> {
        val items: MutableList<ListStoryItem> = arrayListOf()
        for (i in 0..50) {
            val story = ListStoryItem(
                i.toString(),
                "https://picsum.photos/seed/picsum/200/300",
                "Title $i",
                "Description $i",
                "id $i",
                0.0,
                0.0
            )
            items.add(story)
        }
        return items
    }

    fun generateDummyLoginResponse(): LoginResponse {
        return LoginResponse(
            LoginResult(
                "id",
                "name",
                "token"
            ),
            false,
            "token",
        )
    }

    fun generateDummyRegisterResponse(): RegisterResponse{
        return RegisterResponse(
            false,
            "success"
        )
    }
}
