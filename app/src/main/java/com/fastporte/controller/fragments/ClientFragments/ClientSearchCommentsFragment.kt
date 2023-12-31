package com.fastporte.controller.fragments.ClientFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fastporte.R
import com.fastporte.adapter.SearchClientCommentsAdapter
import com.fastporte.helpers.BaseURL
import com.fastporte.models.Comment
import com.fastporte.models.User
import com.fastporte.network.CommentsService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ClientSearchCommentsFragment : Fragment() {
    private var user: User? = null;

    lateinit var commentRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View =
            inflater.inflate(R.layout.fragment_client_search_comments, container, false)

        commentRecyclerView = view.findViewById(R.id.rv_client_search_comments)
        loadComments(view)

        return view
    }

    fun setUser(user: User) {
        this.user = user
    }

    private fun loadComments(view: View) {
        val retrofit = Retrofit.Builder()
            .baseUrl(BaseURL.BASE_URL.toString())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val commentService: CommentsService = retrofit.create(CommentsService::class.java)
        val listComments = commentService.getCommentsByDriverID(user?.id ?: 0)
        listComments.enqueue(object : Callback<List<Comment>> {
            override fun onResponse(
                call: Call<List<Comment>>,
                response: Response<List<Comment>>
            ) {
                val commentList = response.body()
                if (commentList != null) {
                    commentRecyclerView.adapter =
                        SearchClientCommentsAdapter(commentList, requireContext())
                    commentRecyclerView.layoutManager = LinearLayoutManager(context)
                }
            }

            override fun onFailure(call: Call<List<Comment>>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }


}