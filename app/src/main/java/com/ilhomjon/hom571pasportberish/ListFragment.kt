package com.ilhomjon.hom571pasportberish

import Adapters.RvAdapter
import Adapters.RvOnClick
import Database.AppDatabase
import Models.Citizens
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.ilhomjon.hom571pasportberish.databinding.FragmentListBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ListFragment : Fragment() {
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

    lateinit var binding:FragmentListBinding
    lateinit var rvAdapter:RvAdapter
    lateinit var appDatabase: AppDatabase
    lateinit var listData:ArrayList<Citizens>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentListBinding.inflate(LayoutInflater.from(context))

        binding.imageBackList.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.imageListSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.d("search", "Submit")
                var listSearch = ArrayList<Citizens>()
                for (citizens in listData) {
                    if (citizens.name?.subSequence(0, query?.length!!) == query){
                        listSearch.add(citizens)
                    }
                }
                search(listSearch)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.d("search", "change")
                var listSearch = ArrayList<Citizens>()
                for (citizens in listData) {
                    if (citizens.name?.subSequence(0, newText?.length!!).toString().toLowerCase() == newText.toString().toLowerCase()){
                        listSearch.add(citizens)
                    }
                }
                search(listSearch)
                return true
            }
        })
        binding.imageListSearch.setOnCloseListener(object : SearchView.OnCloseListener{
            override fun onClose(): Boolean {
                binding.imageBackList.visibility = View.VISIBLE
                binding.txtTitle.visibility = View.VISIBLE
                search(listData)
                return false
            }
        })
        binding.imageListSearch.setOnSearchClickListener {
            binding.imageBackList.visibility = View.GONE
            binding.txtTitle.visibility = View.GONE
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        appDatabase = AppDatabase.getInstance(context)
        listData = ArrayList<Citizens>()
        listData.addAll(appDatabase.citizenDao().getAllCitizens())

        rvAdapter = RvAdapter(listData, object : RvOnClick {
            override fun itemOnClick(citizens: Citizens, position: Int) {
                findNavController().navigate(
                    R.id.aboutShowFragment,
                    bundleOf("keyCitizen" to citizens)
                )
            }

            override fun moreOnClick(citizens: Citizens, position: Int, v:ImageView) {

                val popupMenu = PopupMenu(context, v)
                popupMenu.inflate(R.menu.popup_menu)
                popupMenu.setOnMenuItemClickListener {
                    when(it.itemId){
                        R.id.delete_menu ->{
                            val dialog = AlertDialog.Builder(context)
                            dialog.setMessage("${citizens.name} ${citizens.passportSeriya} fuqaro o'chirilsinmi?")
                            dialog.setNegativeButton("Ha", object : DialogInterface.OnClickListener {
                                override fun onClick(dialog: DialogInterface?, which: Int) {
                                    var id = appDatabase.citizenDao().getCitizenById(citizens.passportSeriya!!)
                                    citizens.id = id
                                    appDatabase.citizenDao().deleteCitizen(citizens)
                                    Toast.makeText(context, "${citizens.id} deleted", Toast.LENGTH_SHORT).show()
                                    onResume()
                                }
                            })
                            dialog.setPositiveButton("Yo'q", object : DialogInterface.OnClickListener{
                                override fun onClick(dialog: DialogInterface?, which: Int) {

                                }
                            })
                            dialog.show()
                        }
                        R.id.edit_menu ->{
                            findNavController().navigate(R.id.editFragment, bundleOf("citizensKey" to citizens))
                        }
                    }
                    true
                }
                popupMenu.show()
            }
        })

        binding.rvList.adapter = rvAdapter
    }

    fun search(list: List<Citizens>){
        rvAdapter = RvAdapter(list, object : RvOnClick {
            override fun itemOnClick(citizens: Citizens, position: Int) {
                findNavController().navigate(
                    R.id.aboutShowFragment,
                    bundleOf("keyCitizen" to citizens)
                )
            }

            override fun moreOnClick(citizens: Citizens, position: Int, v:ImageView) {
                val popupMenu = PopupMenu(context, v)
                popupMenu.inflate(R.menu.popup_menu)
                popupMenu.setOnMenuItemClickListener {
                    when(it.itemId){
                        R.id.delete_menu ->{
                            val dialog = AlertDialog.Builder(context)
                            dialog.setMessage("${citizens.name} ${citizens.passportSeriya} fuqaro o'chirilsinmi?")
                            dialog.setNegativeButton("Ha", object : DialogInterface.OnClickListener {
                                override fun onClick(dialog: DialogInterface?, which: Int) {
                                    var id = appDatabase.citizenDao().getCitizenById(citizens.passportSeriya!!)
                                    citizens.id = id
                                    appDatabase.citizenDao().deleteCitizen(citizens)
                                    Toast.makeText(context, "${citizens.id} deleted", Toast.LENGTH_SHORT).show()
                                    onResume()
                                }
                            })
                            dialog.setPositiveButton("Yo'q", object : DialogInterface.OnClickListener{
                                override fun onClick(dialog: DialogInterface?, which: Int) {

                                }
                            })
                            dialog.show()
                        }
                        R.id.edit_menu ->{
                            findNavController().navigate(R.id.editFragment, bundleOf("citizensKey" to citizens))
                        }
                    }
                    true
                }
                popupMenu.show()
            }
        })

        binding.rvList.adapter = rvAdapter
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                ListFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}