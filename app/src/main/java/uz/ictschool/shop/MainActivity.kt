package uz.ictschool.shop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import uz.ictschool.shop.fragments.HomeFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        supportFragmentManager
//            .beginTransaction()
//            .add(R.id.main, HomeFragment())
//            .commit()
    }
}