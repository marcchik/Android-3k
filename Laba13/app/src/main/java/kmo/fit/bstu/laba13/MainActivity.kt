package kmo.fit.bstu.laba13

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kmo.fit.bstu.laba13.Adapter.Adapter
import kmo.fit.bstu.laba13.Common.Common
import kmo.fit.bstu.laba13.Interface.RetrofitInterface
import kmo.fit.bstu.laba13.Model.Coin
import kmo.fit.bstu.laba13.Model.Movie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity(), CoroutineScope by MainScope() {

    lateinit var mService: RetrofitInterface
    lateinit var layoutManager: LinearLayoutManager
    lateinit var adapter: Adapter
    lateinit var dialog: AlertDialog
    lateinit var recyclerMovieList: RecyclerView;
    var Lise: List<Coin> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mService = Common.retrofitService
        recyclerMovieList = findViewById(R.id.recyclerMovieList)
        layoutManager = LinearLayoutManager(this)
        recyclerMovieList.layoutManager = layoutManager



        launch(Dispatchers.Main) {
            mService = Common.retrofitService
            mService.getMovieList().enqueue(object : Callback<MutableList<Movie>> {
                override fun onFailure(call: Call<MutableList<Movie>>, t: Throwable) {

                }

                override fun onResponse(
                    call: Call<MutableList<Movie>>,
                    response: Response<MutableList<Movie>>
                ) {
                    adapter = Adapter(baseContext, response.body() as MutableList<Movie>)
                    adapter.notifyDataSetChanged()
                    recyclerMovieList.adapter = adapter

                }
            })
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_recipe, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            R.id.item1 -> {
                val intent = Intent(this, MainActivity::class.java).apply {
                }
                startActivity(intent)
                return true
            }
            R.id.item2 -> {
                val intent = Intent(this, CoinActivity::class.java).apply {
                }
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}


