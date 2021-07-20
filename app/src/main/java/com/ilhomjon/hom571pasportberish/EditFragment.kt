package com.ilhomjon.hom571pasportberish

import Adapters.RvAdapter
import Database.AppDatabase
import Models.Citizens
import android.Manifest
import android.app.AlertDialog
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.github.florent37.runtimepermission.kotlin.askPermission
import com.ilhomjon.hom571pasportberish.databinding.FragmentGivePassportBinding
import com.ilhomjon.hom571pasportberish.databinding.ItemDiaolgTasdiqlashBinding
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class EditFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    lateinit var binding: FragmentGivePassportBinding
    lateinit var appDatabase: AppDatabase
    lateinit var citizens: Citizens

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGivePassportBinding.inflate(LayoutInflater.from(context))

        binding.imageBackAdd.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.imageAdd.setOnClickListener {
            askPermission(Manifest.permission.READ_EXTERNAL_STORAGE) {
                //all permissions already granted or just granted
                getImageContent.launch("image/*")
            }.onDeclined { e ->
                if (e.hasDenied()) {

                    AlertDialog.Builder(context)
                        .setMessage("Please accept our permissions")
                        .setPositiveButton("yes") { dialog, which ->
                            e.askAgain();
                        } //ask again
                        .setNegativeButton("no") { dialog, which ->
                            dialog.dismiss();
                        }
                        .show();
                }

                if (e.hasForeverDenied()) {
                    //the list of forever denied permissions, user has check 'never ask again'

                    // you need to open setting manually if you really need it
                    e.goToSettings();
                }
            }

        }
        appDatabase = AppDatabase.getInstance(context)

        citizens = arguments?.getSerializable("citizensKey") as Citizens
        binding.edtManName.setText(citizens.name)
        binding.edtManSoname.setText(citizens.lastName)
        binding.edtManO.setText(citizens.otasiningIsmi)
        binding.edtShaharTuman.setText(citizens.city)
        binding.edtPassportOlganVaqti.setText(citizens.passportOlganVaqti)
        binding.edtPassportUddati.setText(citizens.passportDedline)
        absolutePath = citizens.imagePath!!
        binding.imageAdd.setImageURI(Uri.parse(citizens.imagePath))
        binding.spinnerViloyat.setSelection(citizens.viloyat!!)
        binding.spinnerJinsi.setSelection(citizens.jinsi!!)
        binding.edtUyManzili.setText(citizens.uyManzili)


        binding.btnSave.setOnClickListener {
            citizens.name = binding.edtManName.text.toString().trim()
            citizens.lastName = binding.edtManSoname.text.toString().trim()
            citizens.otasiningIsmi = binding.edtManO.text.toString().trim()
            citizens.city = binding.edtShaharTuman.text.toString().trim()
            citizens.passportOlganVaqti = binding.edtPassportOlganVaqti.text.toString().trim()
            citizens.passportDedline = binding.edtPassportUddati.text.toString().trim()
            citizens.imagePath = absolutePath
            citizens.viloyat = binding.spinnerViloyat.selectedItemPosition
            citizens.jinsi = binding.spinnerJinsi.selectedItemPosition


            if (citizens.name != "" && citizens.lastName != "" && absolutePath != "") {
                val alertDialog = AlertDialog.Builder(context, R.style.NewDialog)
                val itemDialog = ItemDiaolgTasdiqlashBinding.inflate(LayoutInflater.from(context))
                val dialog = alertDialog.create()
                dialog.setView(itemDialog.root)
                itemDialog.btnNo.setOnClickListener { dialog.cancel() }
                itemDialog.btnYes.setOnClickListener {
                    var id = appDatabase.citizenDao().getCitizenById(citizens.passportSeriya!!)
                    citizens.id = id
                    appDatabase.citizenDao().updateCitizen(citizens)
                    Toast.makeText(context, "${id} ${citizens.name} tahrirlandi", Toast.LENGTH_SHORT)
                        .show()
                    dialog.cancel()

                    findNavController().popBackStack()
                }
                dialog.show()
            } else {
                Toast.makeText(context, "Avval ma'lumotlarni to'ldiring...", Toast.LENGTH_SHORT)
                    .show()
            }

        }
        return binding.root
    }

    var absolutePath = ""
    private val getImageContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri ->
            uri ?: return@registerForActivityResult
            binding.imageAdd.setImageURI(uri)
            val inputStream = activity?.contentResolver?.openInputStream(uri)
            val format = SimpleDateFormat("yyMMdd_hhss").format(Date())
            val file = File(activity?.filesDir, "${format}image.jpg")
            val fileOutputStream = FileOutputStream(file)
            inputStream?.copyTo(fileOutputStream)
            inputStream?.close()
            fileOutputStream.close()
            absolutePath = file.absolutePath

            Toast.makeText(context, "$absolutePath", Toast.LENGTH_SHORT).show()
        }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EditFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}