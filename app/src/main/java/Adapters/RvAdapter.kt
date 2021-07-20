package Adapters

import Models.Citizens
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.ilhomjon.hom571pasportberish.databinding.ItemRvBinding
import java.io.Serializable

class RvAdapter(val list: List<Citizens>, val rvOnClick: RvOnClick)
    : RecyclerView.Adapter<RvAdapter.Vh>(){

    inner class Vh(var itemRvBinding: ItemRvBinding):RecyclerView.ViewHolder(itemRvBinding.root){

        fun onBind(citizens: Citizens, position: Int){
            itemRvBinding.txtItemRvName.text = "${citizens.name} ${citizens.lastName}"
            itemRvBinding.txtItemRvPassportSeriya.text = citizens.passportSeriya
            itemRvBinding.txtItemRvPosition.text = "${position+1}"
            itemRvBinding.cardItemRv.setOnClickListener {
                rvOnClick.itemOnClick(citizens, position)
            }
            itemRvBinding.itemRvMore.setOnClickListener {
                rvOnClick.moreOnClick(citizens, position, itemRvBinding.itemRvMore)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemRvBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position], position)
    }

    override fun getItemCount(): Int = list.size


}
interface RvOnClick{
    fun itemOnClick(citizens: Citizens, position:Int)
    fun moreOnClick(citizens: Citizens, position: Int, v:ImageView)
}