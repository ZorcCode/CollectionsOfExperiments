package com.example.easylife.kotlinfiles

import android.media.CamcorderProfile.get
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.example.easylife.R
import org.json.JSONObject
import java.io.IOException

class RecyclerViewExample : AppCompatActivity() {

    private val movieList = ArrayList<MovieModel>()
    var year = 1900
    private lateinit var recyclerViewAdapter: RecyclerViewAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view_example)

        findViewById<Button>(R.id.view_all).setOnClickListener { viewAll() }
        findViewById<Button>(R.id.add).setOnClickListener { addMovie() }



        val recycler = findViewById<RecyclerView>(R.id.recycler)
        recyclerViewAdapter = RecyclerViewAdapter(movieList)
        val layoutManager = LinearLayoutManager(applicationContext)
        recycler.layoutManager = layoutManager
        recycler.itemAnimator = DefaultItemAnimator()
        recycler.adapter = recyclerViewAdapter

        prepareMovieData()
    }

    private fun prepareMovieData() {
        var movie = MovieModel("Mad Max: Fury Road", "Action & Adventure", "2015")
        movieList.add(movie)
        movie = MovieModel("Inside Out", "Animation, Kids & Family", "2015")
        movieList.add(movie)
        movie = MovieModel("Star Wars: Episode VII - The Force Awakens", "Action", "2015")
        movieList.add(movie)
        movie = MovieModel("Shaun the Sheep", "Animation", "2015")
        movieList.add(movie)
        movie = MovieModel("The Martian", "Science Fiction & Fantasy", "2015")
        movieList.add(movie)
        movie = MovieModel("Mission: Impossible Rogue Nation", "Action", "2015")
        movieList.add(movie)
        movie = MovieModel("Up", "Animation", "2009")
        movieList.add(movie)
        movie = MovieModel("Star Trek", "Science Fiction", "2009")
        movieList.add(movie)
        movie = MovieModel("The LEGO MovieModel", "Animation", "2014")
        movieList.add(movie)
        movie = MovieModel("Iron Man", "Action & Adventure", "2008")
        movieList.add(movie)
        movie = MovieModel("Aliens", "Science Fiction", "1986")
        movieList.add(movie)
        movie = MovieModel("Chicken Run", "Animation", "2000")
        movieList.add(movie)
        movie = MovieModel("Back to the Future", "Science Fiction", "1985")
        movieList.add(movie)
        movie = MovieModel("Raiders of the Lost Ark", "Action & Adventure", "1981")
        movieList.add(movie)
        movie = MovieModel("Goldfinger", "Action & Adventure", "1965")
        movieList.add(movie)
        movie = MovieModel("Guardians of the Galaxy", "Science Fiction & Fantasy", "2014")
        movieList.add(movie)
        recyclerViewAdapter.notifyDataSetChanged()
    }

    fun viewAll() {
        movieList.clear()
        val num = 1
        val map = """
            {
            pageNumber: $num
            }
        """.trimIndent()
        val process = HttpProcess("http://192.168.195.79:3000/getAll")

        process.post(map, object : HttpProcess.Callback {
            override fun onError(exception: IOException?) {
                Log.e("82", "MainHome -> onError: " + exception?.message)
            }

            override fun onResponse(response: String?) {
                Log.e("86", "onResponse: " + response)
                val data = JSONObject(response)
                val array = data.getJSONArray("data")
                for (i in 0 until array.length()) {
                    val item = array.getJSONObject(i)
                    val m = MovieModel(
                        item.getString("name"),
                        item.getString("genre"),
                        item.getString("year")
                    )
                    movieList.add(m)
                }
                runOnUiThread({ recyclerViewAdapter.notifyDataSetChanged() })
            }
        })
    }

    fun addMovie() {
        year += 2
        val name = "Thor" + year
        val genre = "Sci-fi" + year

        val map = """
            {
                "name": $name,
                "year": $year,
                "genre": $genre
            }
        """.trimIndent()

        val process = HttpProcess("http://192.168.195.79:3000/xyz")

        process.post(map, object : HttpProcess.Callback {
            override fun onError(exception: IOException?) {
                Log.e("82", "MainHome -> onError: " + exception?.message)
            }

            override fun onResponse(response: String?) {
                Log.e("207", "MainHome -> onResponse: $response")
                if (response == null)
                    return
                val jsonObject = JSONObject(response).getJSONObject("obj")
                Log.e("89", "onResponse: " + jsonObject.toString(4))
            }
        })

    }

    private fun setRecyclerViewScrollListener() {
//        scrollListener = object : RecyclerView.OnScrollListener() {
//            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
//                super.onScrollStateChanged(recyclerView, newState)
//                val totalItemCount = recyclerView!!.layoutManager.itemCount
//                if (totalItemCount == lastVisibleItemPosition + 1) {
//                    Log.d("MyTAG", "Load new list")
//                    recycler.removeOnScrollListener(scrollListener)
//                }
//            }
//        }
//        recycler.addOnScrollListener(scrollListener)
    }
}