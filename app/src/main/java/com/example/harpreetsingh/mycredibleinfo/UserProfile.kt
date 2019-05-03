package com.example.harpreetsingh.mycredibleinfo

import android.content.Intent
import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.example.harpreetsingh.mycredibleinfo.ProfileFragments.FragmentEducation
import com.example.harpreetsingh.mycredibleinfo.ProfileFragments.FragmentPersonal
import com.example.harpreetsingh.mycredibleinfo.ProfileFragments.FragmentProfessional
import kotlinx.android.synthetic.main.activity_user_profile.*
import org.json.JSONObject


class UserProfile : AppCompatActivity()
{
    var session: Session? = null

    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        session = Session(this@UserProfile)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        title = "User Profile"
        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)

        container.adapter = mSectionsPagerAdapter

        container.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))
        tabs.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(container))

        //Fetching and Displaying Profile
        setProfilePic()

        //User name

        fetchPersonalDetails()




    }
    private fun logout() {
        session!!.setLoggedIn(false)
        finish()
        startActivity(Intent(this@UserProfile, Login::class.java))

    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.user_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            R.id.logout_user -> logout()
        }
        return super.onOptionsItemSelected(item)
    }


    fun fetchPersonalDetails()
    {
        val baseUrl = "http://139.59.65.145:9090/user/personaldetail/${session!!.userID}"

        val rqstQue = Volley.newRequestQueue(this)
        val objReq = StringRequest(
                Request.Method.GET,
                baseUrl,
                Response.Listener
                {
                    response -> Log.i("JSONRESP",response.toString())
                    val jsonObj = JSONObject(response)
                    val respArray: JSONObject = jsonObj.getJSONObject("data")

                    //AppBarLayout Content
                    user_name.text = respArray.get("name").toString()





                },
                Response.ErrorListener
                {
                    error -> Log.i("Error",error.toString())
                }
        )
        rqstQue.add(objReq)
    }
    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm)
    {

        override fun getItem(position: Int): Fragment? = when(position)
        {
            0-> {
                FragmentPersonal()
            }
            1-> {
                FragmentEducation()
            }
            2-> {
                FragmentProfessional()
            }
            else -> null
        }

        override fun getCount(): Int
        {

            return 3
        }

    }




    fun setProfilePic()
    {
        Glide.with(this).load("http://139.59.65.145:9090/user/personaldetail/profilepic/${session!!.userID}").into(profilePicUser)
    }


}
