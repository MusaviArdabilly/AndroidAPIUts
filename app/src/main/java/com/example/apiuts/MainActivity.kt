package com.example.apiuts

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

val url = "192.168.1.14/college/mtandroid_uts"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPreferences = getSharedPreferences("CEKLOGIN", Context.MODE_PRIVATE)
        val stat = sharedPreferences.getString("STATUS", "")

        if (stat == "1") {
            startActivity(Intent(this@MainActivity, DashboardActivity::class.java))
            finish()
        } else {
            btn_Login.setOnClickListener {
                var username = editTextUsername.text.toString()
                var password = editTextPassword.text.toString()
                Log.i("login",username+password)
                postkerserver(username, password)
            }
        }

//        AndroidNetworking.get("http://192.168.1.14/college/mtandroid_uts/getberita.php")
//                .setTag("asd")
//                .setPriority(Priority.MEDIUM)
//                .build()
//                .getAsJSONObject(object : JSONObjectRequestListener {
//                    override fun onResponse(response: JSONObject) {
//                        Log.e("_kotlinResponse", response.toString())
//                        val jsonArray = response.getJSONArray("result")
//                        for (i in 0 until jsonArray.length()){
//                            val jsonObject = jsonArray.getJSONObject(i)
//                            Log.e("_kotlinTitle", jsonObject.optString("penulis_berita"))
//
//                            asd.setText(jsonObject.optString("penulis_berita"))
//                        }
//                    }
//
//                    override fun onError(error: ANError) {
//                        Log.i("_err", error.toString())
//                    }
//                })
    }

    fun postkerserver(data1: String, data2: String) {
        Log.i("login",data1+data2)
        AndroidNetworking.post("http://$url/ceklogin.php")
            .addBodyParameter("username", data1)
            .addBodyParameter("password", data2)
            .setPriority(Priority.MEDIUM).build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {

                    val jsonArray = response.getJSONArray("result")
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        Log.e("_kotlinTitle", jsonObject.optString("status"))
                        var statuslogin = jsonObject.optString("status")
                        loginstatus.setText(statuslogin)
                        if (statuslogin == "1") {
                            val sharedPreferences =
                                getSharedPreferences("CEKLOGIN", Context.MODE_PRIVATE)
                            val editor = sharedPreferences.edit()
                            editor.putString("STATUS", statuslogin)
                            editor.apply()
                            startActivity(Intent(this@MainActivity, DashboardActivity::class.java))
                            finish()
                        }
                    }
                }

                override fun onError(error: ANError) { // handle error
                }
            })
    }
}
