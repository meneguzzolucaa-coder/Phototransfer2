package com.example.phototransfer2

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val selectedUris = mutableListOf<Uri>()
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var shareButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val selectButton: Button = findViewById(R.id.btn_select)
        shareButton = findViewById(R.id.btn_share)
        shareButton.isEnabled = false

        val listView: ListView = findViewById(R.id.list_photos)
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, mutableListOf())
        listView.adapter = adapter

        selectButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
                type = "image/*"
                putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            }
            startActivityForResult(Intent.createChooser(intent, "Seleziona immagini"), REQUEST_CODE_PICK_IMAGES)
        }

        shareButton.setOnClickListener {
            Toast.makeText(this, "Funzione di condivisione LAN non ancora implementata", Toast.LENGTH_SHORT).show()
            // TODO: implementare server e client LAN
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_PICK_IMAGES && resultCode == Activity.RESULT_OK) {
            selectedUris.clear()
            data?.let { result ->
                val clipData = result.clipData
                if (clipData != null) {
                    for (i in 0 until clipData.itemCount) {
                        val item = clipData.getItemAt(i)
                        item.uri?.let { uri -> selectedUris.add(uri) }
                    }
                } else {
                    result.data?.let { uri -> selectedUris.add(uri) }
                }
            }
            // Update adapter with string URIs for display
            adapter.clear()
            adapter.addAll(selectedUris.map { it.toString() })
            adapter.notifyDataSetChanged()
            shareButton.isEnabled = selectedUris.isNotEmpty()
        }
    }

    companion object {
        private const val REQUEST_CODE_PICK_IMAGES = 1001
    }
}
