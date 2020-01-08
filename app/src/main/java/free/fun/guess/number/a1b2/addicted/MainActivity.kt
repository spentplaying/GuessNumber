package free.`fun`.guess.number.a1b2.addicted

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Window

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //hide action bar
        this.supportActionBar?.hide()

        setContentView(R.layout.activity_main)
    }
}
