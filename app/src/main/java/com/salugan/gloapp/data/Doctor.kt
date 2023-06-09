package com.salugan.gloapp.data

import android.os.Parcelable
import com.salugan.gloapp.R
import kotlinx.parcelize.Parcelize

@Parcelize
data class Doctor(
    val name: String,
    val specialist: String,
    val experience: Int,
    val about: String,
    val workingTime: String,
    val consultationFee: Int,
    val photo: Int,
) : Parcelable

val skinSpecialistDoctors = arrayListOf(
    Doctor(
        "Dr. John Smith",
        "Dermatology",
        10,
        "Dr. John Smith is a highly skilled dermatologist with expertise in various skin conditions and treatments.",
        "Mon - Fri (08:00 - 17:00)",
        150000,
        R.drawable.doctor
    ),
    Doctor(
        "Dr. Emily Johnson",
        "Cosmetic Dermatology",
        8,
        "Dr. Emily Johnson specializes in cosmetic dermatology and provides innovative treatments for skin rejuvenation.",
        "Mon - Fri (09:00 - 18:00)",
        200000,
        R.drawable.doctor
    ),
    Doctor(
        "Dr. Sarah Thompson",
        "Pediatric Dermatology",
        12,
        "Dr. Sarah Thompson is a renowned pediatric dermatologist and offers specialized care for children with skin conditions.",
        "Mon - Fri (08:30 - 16:30)",
        180000,
        R.drawable.doctor
    ),
    Doctor(
        "Dr. Michael Brown",
        "Mohs Surgery",
        15,
        "Dr. Michael Brown is an expert in Mohs surgery for skin cancer and provides comprehensive treatment options.",
        "Mon - Fri (09:30 - 17:30)",
        250000,
        R.drawable.doctor
    ),
    Doctor(
        "Dr. Jessica Miller",
        "Aesthetic Dermatology",
        6,
        "Dr. Jessica Miller focuses on aesthetic dermatology and offers a range of cosmetic procedures for skin enhancement.",
        "Mon - Fri (10:00 - 18:00)",
        220000,
        R.drawable.doctor
    )
)