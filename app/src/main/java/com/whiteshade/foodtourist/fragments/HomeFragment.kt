package com.whiteshade.foodtourist.fragments

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.whiteshade.foodtourist.R
import com.whiteshade.foodtourist.adapter.AllRestaurantsAdapter
import com.whiteshade.foodtourist.model.Restaurants
import com.whiteshade.foodtourist.util.ConnectionManager
import com.whiteshade.foodtourist.util.FETCH_RESTAURANTS
import org.json.JSONException
import org.json.JSONObject


class HomeFragment : Fragment() {
    private lateinit var recyclerRestaurant: RecyclerView
    private lateinit var allRestaurantsAdapter: AllRestaurantsAdapter
    private var restaurantList = arrayListOf<Restaurants>()
    private lateinit var progressBar: ProgressBar
    private lateinit var rlLoading: RelativeLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        progressBar = view?.findViewById(R.id.progressBar) as ProgressBar
        rlLoading = view.findViewById(R.id.rlLoading) as RelativeLayout
        rlLoading.visibility = View.VISIBLE

        /*A separate method for setting up our recycler view*/
        setUpRecycler(view)

        return view
    }

    private fun setUpRecycler(view: View) {
        recyclerRestaurant = view.findViewById(R.id.recyclerRestaurants) as RecyclerView

        /*Create a queue for sending the request*/
        val queue = Volley.newRequestQueue(activity as Context)


        /*Check if the internet is present or not*/
        if (ConnectionManager().isNetworkAvailable(activity as Context)) {

            /*Create a JSON object request*/
            val jsonObjectRequest = object : JsonObjectRequest(
                Request.Method.GET,
                FETCH_RESTAURANTS,
                null,
                Response.Listener<JSONObject> { response ->
                    rlLoading.visibility = View.GONE

                    /*Once response is obtained, parse the JSON accordingly*/
                    try {
                        val data = response.getJSONObject("data")
                        val success = data.getBoolean("success")
                        if (success) {

                            val resArray = data.getJSONArray("data")
                            for (i in 0 until resArray.length()) {
                                val resObject = resArray.getJSONObject(i)
                                val restaurant = Restaurants(
                                    resObject.getString("id").toInt(),
                                    resObject.getString("name"),
                                    resObject.getString("rating"),
                                    resObject.getString("cost_for_one").toInt(),
                                    resObject.getString("image_url")
                                )
                                restaurantList.add(restaurant)
                                if (activity != null) {
                                    allRestaurantsAdapter =
                                        AllRestaurantsAdapter(restaurantList, activity as Context)
                                    val mLayoutManager = LinearLayoutManager(activity)
                                    recyclerRestaurant.layoutManager = mLayoutManager
                                    recyclerRestaurant.itemAnimator = DefaultItemAnimator()
                                    recyclerRestaurant.adapter = allRestaurantsAdapter
                                    recyclerRestaurant.setHasFixedSize(true)
                                }

                            }
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { error: VolleyError? ->
                    Toast.makeText(activity as Context, error?.message, Toast.LENGTH_SHORT).show()
                }) {

                /*Send the headers using the below method*/
                override fun getHeaders(): MutableMap<String, String> {
                    val headers = HashMap<String, String>()
                    headers["Content-type"] = "application/json"

                    /*The below used token is my personal  token and might not work provided to me in the training*/
                    headers["token"] = "cbaacb62d23149"
                    return headers
                }
            }

            queue.add(jsonObjectRequest)

        } else {
            val builder = AlertDialog.Builder(activity as Context)
            builder.setTitle("Error")
            builder.setMessage("No Internet Connection found. Please connect to the internet and re-open the app.")
            builder.setCancelable(false)
            builder.setPositiveButton("Ok") { _, _ ->
                ActivityCompat.finishAffinity(activity as Activity)
            }
            builder.create()
            builder.show()
        }

    }


}