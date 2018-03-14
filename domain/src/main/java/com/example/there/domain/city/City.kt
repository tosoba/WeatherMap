package com.example.there.domain.city

import android.os.Parcel
import android.os.Parcelable

data class City(
        val lat: Double,
        val lon: Double,
        val name: String,
        val population: Int
): Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readDouble(),
            parcel.readDouble(),
            parcel.readString(),
            parcel.readInt())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeDouble(lat)
        parcel.writeDouble(lon)
        parcel.writeString(name)
        parcel.writeInt(population)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<City> {
        override fun createFromParcel(parcel: Parcel): City = City(parcel)

        override fun newArray(size: Int): Array<City?> = arrayOfNulls(size)
    }
}