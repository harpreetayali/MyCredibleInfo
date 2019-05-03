package com.example.harpreetsingh.mycredibleinfo.ProfilesEdits

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
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
import kotlinx.android.synthetic.main.activity_education_edit.*
import kotlinx.android.synthetic.main.activity_personal_edit.*
import kotlinx.android.synthetic.main.fragment_education_detail.*
import org.json.JSONObject

class EducationEdit : AppCompatActivity()
{
    private var session: Session? = null
    var rowID:String?=null
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_education_edit)

        session = Session(this@EducationEdit)

        degree_edit.setText(intent.getStringExtra("degree"))
        edu_college_edit.setText(intent.getStringExtra("college"))
        edu_start_edit.setText(intent.getStringExtra("edu_start"))
        edu_end_edit.setText(intent.getStringExtra("edu_end"))
        edu_location_edit.setText(intent.getStringExtra("edu_location"))



      getEduRowID()
        updateEducation.setOnClickListener{
            updateEducationDetail()
        }

        deleteEducation.setOnClickListener {
            deleteEducationDetail()
        }
    }
    fun getEduRowID()
    {
        val baseUrl = "http://139.59.65.145:9090/user/educationdetail/${session!!.userID}"



        val rqstQue = Volley.newRequestQueue(this)
        val objReq = StringRequest(
                Request.Method.GET,
                baseUrl,

                Response.Listener
                {
                    response -> Toast.makeText(this,response.toString(), Toast.LENGTH_LONG).show()
                                val jsonObj = JSONObject(response)
                                val respArray: JSONObject = jsonObj.getJSONObject("data")
                                rowID = respArray.get("id").toString()
                    Toast.makeText(this,rowID,Toast.LENGTH_LONG).show()

                },
                Response.ErrorListener
                {
                    error -> Log.i("Error",error.toString())
                }
        )
        rqstQue.add(objReq)

    }

    fun deleteEducationDetail()
    {
        val baseUrl = "http://139.59.65.145:9090/user/educationdetail/$rowID"



        val rqstQue = Volley.newRequestQueue(this)
        val objReq = StringRequest(
                Request.Method.DELETE,
                baseUrl,
                Response.Listener
                {
                    response ->  this.finish()
                    Toast.makeText(this,response.toString(),Toast.LENGTH_LONG).show()
                    startActivity(Intent(this@EducationEdit, UserProfile::class.java))
                },
                Response.ErrorListener
                {
                    error -> Log.i("Error",error.toString())
                }
        )
        rqstQue.add(objReq)
    }

    fun updateEducationDetail()
    {
        val baseUrl = "http://139.59.65.145:9090/user/educationdetail/${session!!.userID}"

        val jsonobj = JSONObject()
        jsonobj.put("start_year",edu_start_edit.text)
        jsonobj.put("degree",degree_edit.text)
        jsonobj.put("organisation",edu_college_edit.text)
        jsonobj.put("location",edu_location_edit.text)
        jsonobj.put("end_year",edu_end_edit.text)


        val rqstQue = Volley.newRequestQueue(this)
        val objReq = JsonObjectRequest(
                Request.Method.PUT,
                baseUrl,
                jsonobj,
                Response.Listener
                {
                    response ->  this.finish()
                                 Toast.makeText(this,response.toString(),Toast.LENGTH_LONG).show()
                    startActivity(Intent(this@EducationEdit, UserProfile::class.java))
                },
                Response.ErrorListener
                {
                    error -> Log.i("Error",error.toString())
                }
        )
        rqstQue.add(objReq)
    }
}
