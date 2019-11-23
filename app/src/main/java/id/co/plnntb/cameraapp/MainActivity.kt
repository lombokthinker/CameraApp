package id.co.plnntb.cameraapp

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

class MainActivity : AppCompatActivity() {
    val REQUEST_PERMISSION_CODE = 100
    val REQUEST_CAMERA_CODE = 101
    var currentPhoto: String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //check perizinan
        val daftarIzin = mutableListOf<String>()
        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED){
            daftarIzin.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED){
            daftarIzin.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED){
            daftarIzin.add(Manifest.permission.CAMERA)
        }
        if(daftarIzin.size>0){
            val iz = arrayOfNulls<String>(daftarIzin.size)
            for(i in 0 until daftarIzin.size){
                iz[i] = daftarIzin.get(i)
            }
            ActivityCompat.requestPermissions(this, iz, REQUEST_PERMISSION_CODE)
        }else{
            //do nothing
        }

        //tambahkan fungsi click pada ImageView (dg id foto)
        //jadi, ketika di klik, akan memanggil fungsi takePicture
        //yaitu, membuka aplikasi kamera android
        //dan file foto disimpan dengan nama file foto1.jpg
        foto.setOnClickListener{
            takePicture("foto1.jpg")
        }

    }

    fun takePicture(namafile: String){
        //persiapan untuk buka aplikasi kamera bawaan android
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        //siapkan file yang akan menyimpan hasil foto
        val filePhoto = File(getExternalFilesDir(null),namafile)
        //siapkan public URI, jadi aplikasi kamera bisa nyimpan hasil foto
        //di folder aplikasi kita
        val uriPhoto = FileProvider.getUriForFile(this,
            "id.co.plnntb.cameraapp.fileprovider",
            filePhoto
        )
        //ambil lokasi file foto tsb, untuk di tampilkan nanti di ImageView
        currentPhoto = filePhoto.absolutePath
        //infokan ke aplikasi kamera lokasi tempat yimpan hasil fotonya
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriPhoto)
        //start aplikasi kamera
        startActivityForResult(cameraIntent, REQUEST_CAMERA_CODE)
    }

}
