package com.ray.sqlitetemplate.repository.remote_data_source

import android.app.AlertDialog
import androidx.lifecycle.MutableLiveData
import android.os.Build.VERSION_CODES.O
import android.util.Log
import com.ray.sqlitetemplate.BuildConfig
import com.ray.sqlitetemplate.repository.model.MovieData
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/*
    Singleton
    Webservice-like data
 */
object MovieData_Repo {

    private lateinit var mRestService:MovieData_API

    val TAG = "MovieData_Repo"

    fun getDataSet(): MutableLiveData<List<MovieData>>{
        //create OkHTTP client
        val okhttpClientBuilder = OkHttpClient.Builder()

        val logging = HttpLoggingInterceptor()
        //logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        //It runs only in development mode
        //Must be avoid its usage for image/video since it give you lots of byte codes which slows application
        if(BuildConfig.DEBUG){
            //okhttpClientBuilder.addInterceptor(logging)
        }

        val data:MutableLiveData<List<MovieData>> = MutableLiveData()

        //Retrieve data using Retrofit2 from RESTFUL API
        val retrofit: Retrofit =
                Retrofit.Builder()
                        .baseUrl(MovieData_API.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
        mRestService = retrofit.create(MovieData_API::class.java)

        mRestService.getMovieData().enqueue(object: Callback<List<MovieData>> {
            override fun onFailure(call: Call<List<MovieData>>, t: Throwable) {
                data.value= null
                Log.d(TAG,"Something went wrong: Problem calling RestfulAPI",t)
            }

            override fun onResponse(call: Call<List<MovieData>>, response: Response<List<MovieData>>) {
                data.value = response.body()
                Log.d(TAG, "data.value(): ${ data.value?.size}")
            }
        })

        return data
    }
/*
    private fun getDataFromRest() {

        var theData : List<MovieData>
        //Retrieve data using Retrofit2 from RESTFUL API
        var retrofit: Retrofit =
                Retrofit.Builder()
                        .baseUrl(MovieData_API.BASE_URL)
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()

        var mdAPI = retrofit.create(MovieData_API::class.java)
        /*       requests.add(mdAPI.listMovieData())

               val requests = ArrayList<Observable<*>>()


               mCompositeDisposable
                       .add(mdAPI.listMovieData()
                       .subscribeOn(Schedulers.io())
                       .observeOn(AndroidSchedulers.mainThread())
                               .subscribe { t -> convertToArrayList(t) }
                       )

          */

        var callData: Call<List<MovieData>> = mdAPI.getMovieData()

        callData.enqueue(object : Callback<List<MovieData>> {
            override fun onFailure(call: Call<List<MovieData>>?, t: Throwable?) {
                Log.d(TAG, " Something went wrong: ${t!!.message}")

            }

            override fun onResponse(call: Call<List<MovieData>>?, response: Response<List<MovieData>>?) {
                Log.d(TAG, " Success: ${response.toString()}")
                theData = response!!.body()!!
                Log.d(TAG, " onResponse(): ${theData.size}")
                updateView(theData)
            }
        })
    }

    fun updateView(webData: List<MovieData>){
        var MVVM:MovieDataViewModel = MovieDataViewModel()
        MVVM.updateUserView(webData)
    }

    */
}