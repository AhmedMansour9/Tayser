package com.tayser.Fragments

import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.tayser.Activities.NoItemInternetImage
import com.tayser.Adapter.Services_Adapter
import com.tayser.Loading
import com.tayser.Model.*
import com.tayser.R
import com.tayser.ViewModel.*
import com.tayser.utils.ChangeLanguage
import com.tayser.utils.CustomToast
import com.tayser.utils.NetworkCheck
import kotlinx.android.synthetic.main.bottomsheet_service.view.*
import kotlinx.android.synthetic.main.fragment_emergency.view.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Emergency.newInstance] factory method to
 * create an instance of this fragment.
 */
class Emergency : Fragment() {
    private var Datee: String? = null
    private var param2: String? = null
    private var mBehavior: BottomSheetBehavior<*>? = null
    lateinit var views:View
    var dateSetListener: DatePickerDialog.OnDateSetListener? = null
    private var GALLERY = 0

    var file: File? = File("")
    var file2: File? = File("")
    var file3: File? = File("")
    var file4: File? = File("")

    var REQUEST_WRITE_PERMISSION:Int=786


    lateinit var root:View
    lateinit var categories: Sections_Response.Data
    var bundle=Bundle()
    private lateinit var DataSaver: SharedPreferences
    var ServiceId:String?= String()
    var Service: String? =null
    var AddressId: String? =null
    var request_preview: String? =null
    lateinit var date: Calendar
    lateinit var allproducts: Cart_ViewModel
    private var CartNumber = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root= inflater.inflate(R.layout.fragment_emergency, container, false)
        DataSaver = PreferenceManager.getDefaultSharedPreferences(context!!.applicationContext)
        allproducts = ViewModelProvider.NewInstanceFactory().create(
            Cart_ViewModel::class.java)

        init()
        showBottomSheetDialog()
        openBottomSheet()
        getServuces()
        First_Image()
        Second_Image()
        Third_Image()
        Fourth_Image()
        Done()
        GetDate()
        getAddresses()
        openAddress()
        Send_Request()

        return root
    }
    private fun Send_Request() {
        root.Btn_RequestEstablish.setOnClickListener() {
            var RequestViewModel = ViewModelProvider.NewInstanceFactory().create(
                AddMessage_ViewModel::class.java
            )
            if(!NetworkCheck.isConnect(context!!.applicationContext)) {
                startActivity(Intent(context!!.applicationContext, NoItemInternetImage::class.java))
            }
            if (!ValidatAddress() or !ValidateDate() or !ValidateBNumber() or !ValidateFloorNum()) {
                return@setOnClickListener
            }
            if (file.toString().equals("")) {
                file = null
            }
            if (file2.toString().equals("")) {
                file2 = null
            }
            if (file3.toString().equals("")) {
                file3 = null
            }
            if (file4.toString().equals("")) {
                file4 = null
            }




            root.Btn_RequestEstablish.isEnabled = false
            Loading.Show(context!!)
            RequestViewModel.getDataEmergency(
                file,
                file2,
                file3,
                file4,
                categories.id.toString(),
                AddressId!!,
                ServiceId!!,
                root.T_Description.text.toString(),
                Datee!!,
                DataSaver.getString("token", null)!!,
                context!!.applicationContext
            ).observe(this,
                Observer<AddAdress_Response> { loginmodel ->
                    root.Btn_RequestEstablish.isEnabled = true
                    Loading.Disable()
                    if (loginmodel != null) {
                        activity?.let { it1 ->
                            CustomToast.toastIconSuccess(loginmodel.data!!
                                , it1
                            )
                        }
                    } else {

                    }
                })

        }
    }
    private fun Done() {
        root.T_Done.setOnClickListener(){
            ServiceId=""
            Service=""
            Services_Adapter.IdList.forEachIndexed { index, element ->
                var L_Postion:Int= Services_Adapter.IdList.size-1
                if (L_Postion == index) {
                    ServiceId = ServiceId + Services_Adapter.IdList.get(index).getId()
                    Service = Service + Services_Adapter.IdList.get(index).getTitle()

                } else {
                    ServiceId = ServiceId + Services_Adapter.IdList.get(index).getId() + ","
                    Service = Service + Services_Adapter.IdList.get(index).getTitle()+ ","

                }
            }
            root.T_Services.setText(Service)
            mBehavior!!.state=BottomSheetBehavior.STATE_COLLAPSED

        }

    }



    private fun openBottomSheet() {
        root.Img_AddService.setOnClickListener(){

            if (mBehavior!!.state == BottomSheetBehavior.STATE_EXPANDED) {
                mBehavior!!.state = BottomSheetBehavior.STATE_COLLAPSED
            }else {
                mBehavior!!.state=BottomSheetBehavior.STATE_EXPANDED
            }
        }
    }

    private fun init() {
        views = layoutInflater.inflate(R.layout.bottomsheet_service, null)
        mBehavior = BottomSheetBehavior.from(root.bottom_sheet)
        bundle = this.arguments!!
        categories= bundle.getParcelable("CategoryItem")!!


    }



    private fun showBottomSheetDialog() {

        mBehavior?.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_HIDDEN -> {
                    }
                    BottomSheetBehavior.STATE_EXPANDED -> {
                    }
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                    }
                    BottomSheetBehavior.STATE_DRAGGING -> {
                    }
                    BottomSheetBehavior.STATE_SETTLING -> {
                    }
                }
            }

            override fun onSlide(
                bottomSheet: View,
                slideOffset: Float
            ) {
            }
        })

    }

    fun getServuces(){
        var Sizes: Services_ViewModel =  ViewModelProvider.NewInstanceFactory().create(
            Services_ViewModel::class.java)
        this.context!!.applicationContext?.let {
            Sizes.getData(categories.id.toString(), ChangeLanguage.getLanguage(context!!.applicationContext), it).observe(viewLifecycleOwner, Observer<Services_Response> { loginmodel ->
                if(loginmodel!=null) {
                    if(loginmodel.data!!.size>0) {
                        val listAdapter =
                            Services_Adapter(context!!.applicationContext,
                                loginmodel.data as List<Services_Response.Data>
                            )
                        root.recycler_Services.layoutManager = LinearLayoutManager(
                            this.context!!.applicationContext,
                            LinearLayoutManager.VERTICAL,
                            false
                        )
                        root.recycler_Services.setAdapter(listAdapter)
                    }
                }


            })
        }

    }

    fun getAddresses(){
        var Sizes: GetAddress_ViewModel =  ViewModelProvider.NewInstanceFactory().create(
            GetAddress_ViewModel::class.java)
        this.context!!.applicationContext?.let {
            Sizes.getData( DataSaver.getString("token", null)!!,
                ChangeLanguage.getLanguage(context!!.applicationContext), it).observe(viewLifecycleOwner, Observer<ListAddress_Response> { loginmodel ->
                if(loginmodel!=null) {
                    root.T_ChangeAddress.text=resources.getString(R.string.change_address)
                    loginmodel.data!!.forEachIndexed { index, element ->
                        if(loginmodel.data!!.get(index)!!.activate.equals("1")){
                            AddressId=loginmodel.data!!.get(index)!!.id.toString()
                            root.T_address.text=loginmodel.data!!.get(index)!!.country+","+
                                    loginmodel.data!!.get(index)!!.city+","+
                                    loginmodel.data!!.get(index)!!.address
                        }
                    }
                    if(root.T_address.text.isNullOrEmpty()){
                        root.T_address.text=resources.getString(R.string.no_defaddres)
                        root.T_ChangeAddress.text=resources.getString(R.string.change_address)

                    }

                }else {
                    root.T_address.text=resources.getString(R.string.no_defaddres)
                    root.T_ChangeAddress.text=resources.getString(R.string.add_address)
                }
            })
        }

    }


    fun showDateTimePicker() {
        val currentDate = Calendar.getInstance()
        date = Calendar.getInstance()

        var dialog= DatePickerDialog(

            context!!,R.style.DialogTheme,
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                date.set(year, monthOfYear, dayOfMonth)

                TimePickerDialog(
                    context, R.style.DialogTheme,
                    TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                        date.set(Calendar.HOUR_OF_DAY, hourOfDay)
                        date.set(Calendar.MINUTE, minute)
                        root.T_Date.setText("$year-$monthOfYear-$dayOfMonth $hourOfDay:$minute")
                        Datee="$year-$monthOfYear-$dayOfMonth $hourOfDay:$minute"
                    },
                    currentDate[Calendar.HOUR_OF_DAY],
                    currentDate[Calendar.MINUTE],
                    false
                ).show()

            },
            currentDate[Calendar.YEAR],
            currentDate[Calendar.MONTH],
            currentDate[Calendar.DATE]

        )

        dialog.getDatePicker().setMinDate(date.getTimeInMillis());

        dialog.show()
    }

    fun GetDate() {
        root.T_Date.setOnClickListener(View.OnClickListener { showDateTimePicker() })
        dateSetListener =
            DatePickerDialog.OnDateSetListener { datePicker, i, i1, i2 -> root.T_Date.setText("$i-$i1-$i2") }
    }



    private fun openAddress() {
        root.T_ChangeAddress.setOnClickListener() {
            var productsByid = Address()
            val bundle = Bundle()
            productsByid.arguments = bundle
            activity!!.supportFragmentManager.beginTransaction()
                .add(R.id.Rela_Home, productsByid)
                .addToBackStack(null).commit()

        }
    }


    @Subscribe(sticky = false, threadMode = ThreadMode.MAIN)
    fun onMessageEvent( messsg: MessageEvent) {/* Do something */
        Log.d("IGNORE", "Logging view to curb warnings: $messsg")

        getAddresses()

    };
    override fun onAttach(context: Context) {
        super.onAttach(context)
        checkNetwork()
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }

    }

    override fun onActivityResult(requestCode:Int, resultCode:Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1)
        {
            if (data != null)
            {
                val contentURI = data!!.data
                val filePath = getRealPathFromURIPath(contentURI!!,
                    this.context as Activity
                )
                file = File(filePath)
                try
                {
                    val bitmap = MediaStore.Images.Media.getBitmap(context!!.contentResolver, contentURI)
                    val path = saveImage(bitmap)
                    Glide.with(context!!.applicationContext).load(file).into(root.Img_1);

                    root.Img_Addphoto1.visibility=View.GONE

                }
                catch (e: IOException) {
                    e.printStackTrace()
                }

            }

        }
        if (requestCode == 2)
        {
            if (data != null)
            {
                val contentURI = data!!.data
                val filePath = getRealPathFromURIPath(contentURI!!,
                    this.context as Activity
                )
                file2 = File(filePath)
                try
                {
                    val bitmap = MediaStore.Images.Media.getBitmap(context!!.contentResolver, contentURI)
                    val path = saveImage(bitmap)
                    Glide.with(context!!.applicationContext).load(file2).into(root.Img_2);

                    root.Img_Addphoto2.visibility=View.GONE

                }
                catch (e: IOException) {
                    e.printStackTrace()
                }

            }

        }
        if (requestCode == 3)
        {
            if (data != null)
            {
                val contentURI = data!!.data
                val filePath = getRealPathFromURIPath(contentURI!!,
                    this.context as Activity
                )
                file3 = File(filePath)
                try
                {
                    val bitmap = MediaStore.Images.Media.getBitmap(context!!.contentResolver, contentURI)
                    val path = saveImage(bitmap)
                    Glide.with(context!!.applicationContext).load(file3).into(root.Img_3);
                    root.Img_Addphoto3.visibility=View.GONE

                }
                catch (e: IOException) {
                    e.printStackTrace()
                }

            }

        }
        if (requestCode == 4)
        {
            if (data != null)
            {
                val contentURI = data!!.data
                val filePath = getRealPathFromURIPath(contentURI!!,
                    this.context as Activity
                )
                file4 = File(filePath)
                try
                {
                    val bitmap = MediaStore.Images.Media.getBitmap(context!!.contentResolver, contentURI)
                    val path = saveImage(bitmap)
                    Glide.with(context!!.applicationContext).load(file4).into(root.Img_4);
                    root.Img_Addphoto4.visibility=View.GONE
                }
                catch (e: IOException) {
                    e.printStackTrace()
                }

            }

        }

    }

    private fun getRealPathFromURIPath(contentURI: Uri, activity: Activity): String {
        val cursor = activity.contentResolver.query(contentURI, null, null, null, null)
        if (cursor == null) {
            return contentURI.getPath()!!
        } else {
            cursor.moveToFirst()
            val idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
            return cursor.getString(idx)
        }
    }

    private fun First_Image() {
        root.Img_Addphoto1.setOnClickListener(){
            val permission = ContextCompat.checkSelfPermission(context!!,
                Manifest.permission.READ_EXTERNAL_STORAGE)

            if (permission != PackageManager.PERMISSION_GRANTED) {
                makeRequest()
            }else {
                GALLERY=1
                choosePhotoFromGallary(GALLERY)

            }
        }
    }

    private fun Second_Image() {
        root.Img_Addphoto2.setOnClickListener(){
            val permission = ContextCompat.checkSelfPermission(context!!,
                Manifest.permission.READ_EXTERNAL_STORAGE)

            if (permission != PackageManager.PERMISSION_GRANTED) {
                makeRequest()
            }else {
                GALLERY=2
                choosePhotoFromGallary(GALLERY)

            }
        }
    }
    private fun Third_Image() {
        root.Img_Addphoto3.setOnClickListener(){
            val permission = ContextCompat.checkSelfPermission(context!!,
                Manifest.permission.READ_EXTERNAL_STORAGE)

            if (permission != PackageManager.PERMISSION_GRANTED) {
                makeRequest()
            }else {
                GALLERY=3
                choosePhotoFromGallary(GALLERY)

            }
        }
    }
    private fun Fourth_Image() {
        root.Img_Addphoto4.setOnClickListener(){
            val permission = ContextCompat.checkSelfPermission(context!!,
                Manifest.permission.READ_EXTERNAL_STORAGE)

            if (permission != PackageManager.PERMISSION_GRANTED) {
                makeRequest()
            }else {
                GALLERY=4
                choosePhotoFromGallary(GALLERY)

            }
        }
    }
    fun choosePhotoFromGallary(number:Int) {
        val galleryIntent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent, number)
    }

    private fun makeRequest() {
        ActivityCompat.requestPermissions(
            context as Activity,
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE),
            REQUEST_WRITE_PERMISSION)
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_WRITE_PERMISSION -> {

                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {

                } else {
                    choosePhotoFromGallary(GALLERY)
                }
            }
        }
    }

    private fun setupPermissions() {
        val permission = ContextCompat.checkSelfPermission(context!!,
            Manifest.permission.READ_EXTERNAL_STORAGE)

        if (permission != PackageManager.PERMISSION_GRANTED) {
            makeRequest()
        }
    }

    fun saveImage(myBitmap: Bitmap):String {
        val bytes = ByteArrayOutputStream()
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes)
        val wallpaperDirectory = File(
            (Environment.getExternalStorageDirectory()).toString() + IMAGE_DIRECTORY)
        // have the object build the directory structure, if needed.
//        Log.d("fee",wallpaperDirectory.toString())
        if (!wallpaperDirectory.exists())
        {

            wallpaperDirectory.mkdirs()
        }

        try
        {
//            Log.d("heel",wallpaperDirectory.toString())
            val f = File(wallpaperDirectory, ((Calendar.getInstance()
                .getTimeInMillis()).toString() + ".jpg"))
            f.createNewFile()
            val fo = FileOutputStream(f)
            fo.write(bytes.toByteArray())
            MediaScannerConnection.scanFile(context!!,
                arrayOf(f.getPath()),
                arrayOf("image/jpeg"), null)
            fo.close()
//            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath())

            return f.getAbsolutePath()
        }
        catch (e1: IOException) {
            e1.printStackTrace()
        }

        return ""
    }
    companion object {
        private val IMAGE_DIRECTORY = "/demonuts"
    }

    fun checkNetwork(){
        if(!NetworkCheck.isConnect(context!!.applicationContext)) {
            startActivity(Intent(context!!.applicationContext, NoItemInternetImage::class.java))
        }

    }

    private fun ValidateBNumber():Boolean{
        val Fullname=root.T_Services.text.toString()
        if(Fullname.isEmpty()){
            activity?.let { it1 ->
                CustomToast.toastIconError(resources.getString(R.string.selectservice)!!
                    , it1
                )
            }
            return false
        }
        else {
            root.T_Services.error=null
            return true
        }
    }

    private fun ValidateFloorNum():Boolean{
        val Fullname=root.T_Description.text.toString()
        if(Fullname.isEmpty()){
            root.T_Description.error=resources.getString(R.string.feildempty)
            return false
        }
        else {
            root.T_Description.error=null
            return true
        }
    }

    private fun ValidateDate():Boolean{
        if(Datee.isNullOrEmpty()){
            activity?.let { it1 ->
                CustomToast.toastIconError(resources.getString(R.string.choose_Date)!!
                    , it1
                )
            }
            return false
        }
        else {
            return true
        }
    }
    private fun ValidatAddress():Boolean{
        if(AddressId.isNullOrEmpty()){
            activity?.let { it1 ->
                CustomToast.toastIconError(resources.getString(R.string.noaddresss)!!
                    , it1
                )
            }
            return false
        }
        else {

            return true
        }
    }



}