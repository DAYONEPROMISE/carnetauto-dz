package dz.carnautodz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import dz.carnautodz.ui.CarnetApp
import dz.carnautodz.ui.CarnetViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val vm = ViewModelProvider(this)[CarnetViewModel::class.java]
        setContent { CarnetApp(vm) }
    }
}
