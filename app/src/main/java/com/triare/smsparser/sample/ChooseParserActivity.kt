package com.triare.smsparser.sample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_choose_parser.*

class ChooseParserActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_parser)

        smsParser.setOnClickListener {
            val intent = Intent(this, MessageActivity::class.java)
            startActivity(intent)
        }
        smsPatternParser.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}