package com.example.harpreetsingh.mycredibleinfo.ProfileFragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.util.Log

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.harpreetsingh.mycredibleinfo.ProfilesEdits.PersonalEdit

import com.example.harpreetsingh.mycredibleinfo.R
import com.example.harpreetsingh.mycredibleinfo.UserProfile
import kotlinx.android.synthetic.main.activity_user_profile.*
import kotlinx.android.synthetic.main.fragment_pesonal_detail.*
import org.json.JSONObject


class FragmentPersonal:Fragment()
{

    companion object {

        fun newInstance(): FragmentPersonal {
            return FragmentPersonal()
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {


      val view:View  = inflater.inflate(R.layout.fragment_pesonal_detail, container,false)

        var userProfile:UserProfile = activity as UserProfile

        fetchPersonalDetails(userProfile.session!!.userID,userProfile)

        var fabEdit = view.findViewById<View>(R.id.floatingPersonalEdit)
        fabEdit.setOnClickListener {
            var personalEditIntent = Intent(userProfile, PersonalEdit::class.java)
            personalEditIntent.putExtra("name",userProfile.user_name.text)
            personalEditIntent.putExtra("mobile",userProfile.per_mobile.text)
            personalEditIntent.putExtra("skills",userProfile.per_skills.text)
            personalEditIntent.putExtra("links",userProfile.per_links.text)
            personalEditIntent.putExtra("location",userProfile.per_location.text)

            userProfile.finish()
            startActivity(personalEditIntent)
        }

        return view
    }
    fun fetchPersonalDetails(userID:String,ctx:Context)
    {
        val baseUrl = "http://139.59.65.145:9090/user/personaldetail/$userID"

        val rqstQue = Volley.newRequestQueue(ctx)
        val objReq = StringRequest(
                Request.Method.GET,
                baseUrl,
                Response.Listener
                {
                    response -> Log.i("JSONRESP",response.toString())
                    val jsonObj = JSONObject(response)
                    val respArray: JSONObject = jsonObj.getJSONObject("data")

                    //AppBarLayout Content
                    //user_name.text = respArray.get("name").toString()

                    //Personal Fragment Content
                    per_mobile.text = respArray.get("mobile_no").toString()
                    per_skills.text = respArray.get("skills").toString()
                    per_links.text = respArray.get("links").toString()
                    per_location.text = respArray.get("location").toString()



                },
                Response.ErrorListener
                {
                    error -> Log.i("Error",error.toString())
                }
        )
        rqstQue.add(objReq)
    }


}