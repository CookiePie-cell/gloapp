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
        "As a highly skilled dermatologist with over 10 years of experience, I specialize in diagnosing and treating various skin conditions. My expertise includes acne treatment, skin cancer screenings, and dermatological surgeries. I am dedicated to providing personalized care to my patients, ensuring their skin health and well-being. I am passionate about dermatology and committed to staying up-to-date with the latest advancements in the field.",
        "Mon - Fri (08:00 - 17:00)",
        150000,
        R.drawable.doctor
    ),
    Doctor(
        "Dr. Emily Johnson",
        "Cosmetic Dermatology",
        8,
        "With 8 years of experience in cosmetic dermatology, I specialize in providing innovative treatments for skin rejuvenation. My focus includes procedures such as Botox, dermal fillers, and laser treatments. I am dedicated to helping my patients achieve their desired aesthetic goals while ensuring their safety and satisfaction. By staying abreast of the latest advancements in cosmetic dermatology, I provide cutting-edge and personalized care.",
        "Mon - Fri (09:00 - 18:00)",
        200000,
        R.drawable.doctor1
    ),
    Doctor(
        "Dr. Sarah Thompson",
        "Pediatric Dermatology",
        12,
        "As a renowned pediatric dermatologist, I offer specialized care for children with various skin conditions. With 12 years of experience, I have a deep understanding of pediatric dermatology and provide comprehensive treatments for issues such as eczema, psoriasis, and birthmarks. I am committed to creating a comfortable and child-friendly environment, ensuring positive experiences for both children and their parents during their visits.",
        "Mon - Fri (08:30 - 16:30)",
        180000,
        R.drawable.doctor2
    ),
    Doctor(
        "Dr. Michael Brown",
        "Mohs Surgery",
        15,
        "With expertise in Mohs surgery for skin cancer, I provide comprehensive treatment options to my patients. With 15 years of experience, I am highly skilled in performing precise surgical techniques to remove cancerous skin cells while preserving healthy tissue. I prioritize patient education and support throughout the entire treatment process, ensuring the best possible outcomes and patient satisfaction.",
        "Mon - Fri (09:30 - 17:30)",
        250000,
        R.drawable.doctor3
    ),
    Doctor(
        "Dr. Jessica Miller",
        "Aesthetic Dermatology",
        6,
        "As a dermatologist specializing in aesthetic dermatology, I focus on enhancing the natural beauty of my patients' skin. I offer a wide range of cosmetic procedures, including chemical peels, microdermabrasion, and non-surgical facelifts. My goal is to help my patients achieve youthful and radiant skin while prioritizing their safety and well-being. I believe in the transformative power of aesthetic dermatology and provide personalized treatment plans tailored to individual needs.",
        "Mon - Fri (10:00 - 18:00)",
        220000,
        R.drawable.doctor4
    )
)