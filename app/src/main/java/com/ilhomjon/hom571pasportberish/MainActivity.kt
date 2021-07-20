package com.ilhomjon.hom571pasportberish

import android.app.AlertDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.navigation.Navigation
import com.ilhomjon.hom571pasportberish.databinding.ActivityMainBinding
import com.ilhomjon.hom571pasportberish.databinding.FragmentHomeBinding
import com.ilhomjon.hom571pasportberish.databinding.ItemEnterDialogBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    lateinit var dialog:AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        val alertDialog = AlertDialog.Builder(this, R.style.NewDialog)
//        dialog = alertDialog.create()
//
//        val dialogView = ItemEnterDialogBinding.inflate(layoutInflater, binding.mainRoot, false)
//        dialog.setView(dialogView.root)
//
//        dialog.show()
//        SmsDialog().start()
    }



inner class SmsDialog():Thread(){
    override fun run() {
        super.run()
        sleep(3000)
        dialog.cancel()
    }
}
    //bu metod navigation shu activityda boshlanadi va bizga homeFragmentni ochib beradi
    override fun onSupportNavigateUp(): Boolean {
        return Navigation.findNavController(this, R.id.my_navigation_host).navigateUp()
    }
}