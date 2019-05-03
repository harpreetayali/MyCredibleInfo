package com.example.harpreetsingh.mycredibleinfo.ProfilesEdits

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

import com.example.harpreetsingh.mycredibleinfo.R
import com.example.harpreetsingh.mycredibleinfo.Session
import com.example.harpreetsingh.mycredibleinfo.UserProfile
import kotlinx.android.synthetic.main.activity_personal_edit.*
import org.json.JSONObject

class PersonalEdit : AppCompatActivity()
{
    private var session: Session? = null
    var rowID:String?=null
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personal_edit)

        session = Session(this@PersonalEdit)

        name_edit.setText(intent.getStringExtra("name"))
        mobile_edit.setText(intent.getStringExtra("mobile"))
        skills_edit.setText(intent.getStringExtra("skills"))
        links_edit.setText(intent.getStringExtra("links"))
        location_edit.setText(intent.getStringExtra("location"))
        email_edit.setText(session!!.userEmail)


//        getEduRowID()
        updatePersonal.setOnClickListener{
            updatePersonalDetail()
        }


    }
//    fun getEduRowID()
//    {
//        val baseUrl = "http://139.59.65.145:9090/user/educationdetail/${session!!.userID}"
//
//
//
//        val rqstQue = Volley.newRequestQueue(this)
//        val objReq = StringRequest(
//                Request.Method.GET,
//                baseUrl,
//
//                Response.Listener
//                {
//                    response -> Toast.makeText(this,response.toString(), Toast.LENGTH_LONG).show()
//                                val jsonObj = JSONObject(response)
//                                val respArray: JSONObject = jsonObj.getJSONObject("data")
//                                rowID = respArray.get("id").toString()
//                    Toast.makeText(this,rowID,Toast.LENGTH_LONG).show()
//
//                },
//                Response.ErrorListener
//                {
//                    error -> Log.i("Error",error.toString())
//                }
//        )
//        rqstQue.add(objReq)
//
//    }



    fun updatePersonalDetail()
    {
        val baseUrl = "http://139.59.65.145:9090/user/personaldetail/${session!!.userID}"

        val jsonobj = JSONObject()
        jsonobj.put("skills",skills_edit.text)
        jsonobj.put("mobile_no",mobile_edit.text)
        jsonobj.put("name",name_edit.text)
        jsonobj.put("links",links_edit.text)
        jsonobj.put("location",location_edit.text)
        jsonobj.put("email",email_edit.text)

        val rqstQue = Volley.newRequestQueue(this)
        val objReq = JsonObjectRequest(
                Request.Method.PUT,
                baseUrl,
                jsonobj,
                Response.Listener
                {
                    response ->  this.finish()
                                 Toast.makeText(this,response.toString(),Toast.LENGTH_LONG).show()
                    startActivity(Intent(this@PersonalEdit, UserProfile::class.java))
                },
                Response.ErrorListener
                {
                    error -> Log.i("Error",error.toString())
                }
        )
        rqstQue.add(objReq)
    }
}
