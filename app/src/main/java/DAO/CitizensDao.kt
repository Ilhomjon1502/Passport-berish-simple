package DAO

import Models.Citizens
import androidx.room.*

@Dao
interface CitizensDao {

    @Query("select * from citizens")
    fun getAllCitizens():List<Citizens>

    @Insert
    fun addCitizens(citizens: Citizens)

    @Delete
    fun deleteCitizen(citizens: Citizens)

    @Update
    fun updateCitizen(citizens: Citizens)

    @Query("select * from citizens where id=:id")
    fun getCitizenById(id:Int): Citizens

    @Query("select * from citizens where passportSeriya=:passportSeriyaN")
    fun getCitizenById(passportSeriyaN:String):Int
}