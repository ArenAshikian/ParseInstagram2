package com.example.ParseInstaGram_Aren.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ParseInstaGram_Aren.Post;
import com.example.ParseInstaGram_Aren.PostsAdapter;
import com.example.ParseInstaGram_Aren.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PostsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PostsFragment extends Fragment {
    private RecyclerView rvPosts;
    protected PostsAdapter adapter;
    protected List<Post> allPosts;
    SwipeRefreshLayout swiperContainer;


    public static final String TAG= "PostsFragment";
    public PostsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_posts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        swiperContainer=view.findViewById(R.id.swiperContainer);
        swiperContainer.setColorSchemeResources(android.R.color.holo_blue_bright,

                android.R.color.holo_green_light,

                android.R.color.holo_orange_light,

                android.R.color.holo_red_light);
        rvPosts=view.findViewById(R.id.rvPosts);

        allPosts= new ArrayList<>();
        adapter = new PostsAdapter(getContext(),allPosts);
        swiperContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.i(TAG, "fetching new data");
                queryPost();

            }
        });
        rvPosts.setAdapter(adapter);
        rvPosts.setLayoutManager(new LinearLayoutManager(getContext()));
        queryPost();

    }
        protected void queryPost(){
            ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
            query.include(Post.KEY_USER);
            query.setLimit(20);
            query.addDescendingOrder(Post.KEY_CREATED_KEY);
            query.findInBackground(new FindCallback<Post>() {
                @Override
                public void done(List<Post> posts, ParseException e) {
                    if(e != null){
                        Log.e("TAG", "Issue with getting post", e);
                        return;
                    }
                    for(Post post: posts){
                        Log.i("TAG", "Post: " + post.getDescription() + ",username: " + post.getUser().getUsername());
                    }
                    adapter.clear();
                    allPosts.addAll(posts);
                    swiperContainer.setRefreshing(false);
                    adapter.notifyDataSetChanged();

                }
            });
        }
    }
