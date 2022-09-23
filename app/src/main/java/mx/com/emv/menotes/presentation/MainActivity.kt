package mx.com.emv.menotes.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import mx.com.emv.menotes.R
import mx.com.emv.menotes.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        //setSupportActionBar(binding.materialToolbar)
        //supportActionBar?.title = resources.getString(R.string.notes_fragment_title)
    }
}