package com.example.harpreetsingh.mycredibleinfo

import android.app.Activity
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_person_details.*
import android.content.Intent
import android.graphics.Bitmap
import android.widget.Toast
import android.graphics.BitmapFactory
import android.provider.MediaStore
import java.util.*
import android.graphics.Bitmap.CompressFormat
import android.util.Base64
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.io.ByteArrayOutputStream


class PersonDetails : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_person_details)



        profilePic.setOnClickListener{
            val GaleryIntent = Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(GaleryIntent, Context.CONTEXT_INCLUDE_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Context.CONTEXT_INCLUDE_CODE && resultCode == Activity.RESULT_OK && null != data) {
            val selectedImage = data.data
            val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)

            val selectedCursor = contentResolver.query(selectedImage!!, filePathColumn, null, null, null)
            selectedCursor!!.moveToFirst()

            val columnIndex = selectedCursor.getColumnIndex(filePathColumn[0])
            val picturePath = selectedCursor.getString(columnIndex)
            selectedCursor.close()

            //  Drawable d = new BitmapDrawable(getResources(),BitmapFactory.decodeFile(picturePath));
            // btnOpenGalery .setImageBitmap(d);
            profilePic.setImageBitmap(BitmapFactory.decodeFile(picturePath))

            var bitmap:Bitmap = BitmapFactory.decodeFile(picturePath)


            uploadImage( convertImage(bitmap))
            Toast.makeText(applicationContext, picturePath, Toast.LENGTH_SHORT).show()

        }

    }

    fun convertImage(bitmap:Bitmap):String
    {
        val stream = ByteArrayOutputStream()
        bitmap.compress(CompressFormat.JPEG, 70, stream)
        val byteFormat = stream.toByteArray()
        // get the base 64 string
        return Base64.encodeToString(byteFormat,Base64.DEFAULT)
    }
    fun uploadImage(image:String)
    {
        var baseUrl = "http://139.59.65.145:9090/user/personaldetail/pp/post"
        var jsonobj = JSONObject()
        jsonobj.put("photo",image)
        jsonobj.put("uid","1144")
        var rqstQue = Volley.newRequestQueue(this)
        var objReq = JsonObjectRequest(
                Request.Method.POST,
                baseUrl,
                jsonobj,
                Response.Listener
                {
                    response -> Toast.makeText(this,response.toString(),Toast.LENGTH_LONG).show()
                },
                Response.ErrorListener
                {
                    error -> Log.i("Error",error.toString())
                }
        )
        rqstQue.add(objReq)
    }

}
