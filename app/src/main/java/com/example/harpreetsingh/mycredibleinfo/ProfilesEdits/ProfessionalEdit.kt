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
import kotlinx.android.synthetic.main.activity_professional_edit.*
import org.json.JSONObject

class ProfessionalEdit : AppCompatActivity()
{
    private var session: Session? = null
    var rowID:String?=null
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_professional_edit)

        session = Session(this@ProfessionalEdit)

        organisation_edit.setText(intent.getStringExtra("organisation"))
        designation_edit.setText(intent.getStringExtra("designation"))
        pro_start_edit.setText(intent.getStringExtra("pro_start"))
        pro_end_edit.setText(intent.getStringExtra("pro_end"))


        getEduRowID()
        updateProfessional.setOnClickListener{
            updateProfessionalDetail()
        }

        deleteProfessional.setOnClickListener {
            deleteProfessionalDetail()
        }
    }
    fun getEduRowID()
    {
        val baseUrl = "http://139.59.65.145:9090/user/professionaldetail/${session!!.userID}"



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

    fun deleteProfessionalDetail()
    {
        val baseUrl = "http://139.59.65.145:9090/user/professionaldetail/$rowID"



        val rqstQue = Volley.newRequestQueue(this)
        val objReq = StringRequest(
                Request.Method.DELETE,
                baseUrl,
                Response.Listener
                {
                    response ->  this.finish()
                    Toast.makeText(this,response.toString(),Toast.LENGTH_LONG).show()
                    startActivity(Intent(this@ProfessionalEdit, UserProfile::class.java))
                },
                Response.ErrorListener
                {
                    error -> Log.i("Error",error.toString())
                }
        )
        rqstQue.add(objReq)
    }


    fun updateProfessionalDetail()
    {
        val baseUrl = "http://139.59.65.145:9090/user/professionaldetail/${session!!.userID}"

        val jsonobj = JSONObject()
        jsonobj.put("end_date",pro_end_edit.text)
        jsonobj.put("organisation",organisation_edit.text)
        jsonobj.put("designation",designation_edit.text)
        jsonobj.put("start_date",pro_start_edit.text)


        val rqstQue = Volley.newRequestQueue(this)
        val objReq = JsonObjectRequest(
                Request.Method.PUT,
                baseUrl,
                jsonobj,
                Response.Listener
                {
                    response ->  this.finish()
                                 Toast.makeText(this,response.toString(),Toast.LENGTH_LONG).show()
                    startActivity(Intent(this@ProfessionalEdit, UserProfile::class.java))
                },
                Response.ErrorListener
                {
                    error -> Log.i("Error",error.toString())
                }
        )
        rqstQue.add(objReq)
    }
}
