package com.ilhomjon.hom571pasportberish

import Models.Citizens
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ilhomjon.hom571pasportberish.databinding.FragmentAboutShowBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class AboutShowFragment : Fragment() {
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

    lateinit var binding:FragmentAboutShowBinding
    lateinit var citizens: Citizens

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAboutShowBinding.inflate(LayoutInflater.from(context))

        binding.imageBackAbout.setOnClickListener {
            findNavController().popBackStack()
        }

        citizens = arguments?.getSerializable("keyCitizen") as Citizens
        binding.imageShow.setImageURI(Uri.parse(citizens.imagePath))
        if (citizens.jinsi == 0){
            binding.txtShowJinsi.text = "Erkak"
        }else{
            binding.txtShowJinsi.text = "Ayol"
        }
        when(citizens.viloyat){
            0 -> binding.txtShowViloyat.text = "Toshkent"
            1 -> binding.txtShowViloyat.text = "Andijon"
            2 -> binding.txtShowViloyat.text = "Farg'ona"
            3 -> binding.txtShowViloyat.text = "Namangan"
            4 -> binding.txtShowViloyat.text = "Jizzax"
            5 -> binding.txtShowViloyat.text = "Sirdaryo"
            6 -> binding.txtShowViloyat.text = "Navoiy"
            7 -> binding.txtShowViloyat.text = "Surxondaryo"
            8 -> binding.txtShowViloyat.text = "Qashqadaryo"
            9 -> binding.txtShowViloyat.text = "Samarqand"
            10 -> binding.txtShowViloyat.text = "Buxoro"
            11-> binding.txtShowViloyat.text = "Xorazm"
            12 -> binding.txtShowViloyat.text = "Qoraqalpog'iston respublikasi"
        }
        binding.txtShowName.text = "${citizens.name} ${citizens.lastName}"
        binding.txtShowPassportOlganVaqti.text = citizens.passportOlganVaqti
        binding.txtShowShaharTuman.text = citizens.city
        binding.txtShowUyningManzili.text = citizens.uyManzili

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AboutShowFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}