package Models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
class Citizens :Serializable{
    @PrimaryKey(autoGenerate = true)
    var id:Int? = null
    var name:String? = null
    var lastName:String? = null
    var otasiningIsmi:String? = null
    var viloyat:Int? = null
    var city:String? = null
    var uyManzili:String? = null
    var passportOlganVaqti:String? = null
    var passportDedline:String? = null
    var jinsi:Int? = null
    var imagePath:String? = null
    var passportSeriya:String? = null
}