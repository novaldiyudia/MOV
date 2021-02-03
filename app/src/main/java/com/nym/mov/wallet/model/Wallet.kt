package com.nym.mov.wallet.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Wallet (
    var title: String ?="",
    var date: String ?="",
    var money: Double,
    var status: String ?=""
) : Parcelable