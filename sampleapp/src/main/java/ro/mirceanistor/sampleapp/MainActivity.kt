package ro.mirceanistor.sampleapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_start_operation.setOnClickListener {
            startLongRunningOperation()
        }

    }

    @SuppressLint("SetTextI18n")
    fun startLongRunningOperation() {
        progress_indicator.visibility = View.VISIBLE
        Handler(mainLooper).postDelayed(
            {
                operation_result.text = "all done"
                progress_indicator.visibility = View.GONE
            },
            1000
        )
    }
}
