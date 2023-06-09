package com.salugan.gloapp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.salugan.gloapp.R
import com.salugan.gloapp.data.Doctor
import com.salugan.gloapp.databinding.DoctorItemBinding
import com.salugan.gloapp.ui.activities.detail.DoctorDetailActivity

class ListDoctorAdapter(private val listDoctors: ArrayList<Doctor>)
    : RecyclerView.Adapter<ListDoctorAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DoctorItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = listDoctors.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val doctor: Doctor = listDoctors[position]
        holder.bind(doctor)
    }

    class ViewHolder(val binding: DoctorItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(doctor: Doctor) {
            binding.apply {
                profileImageItem.setImageResource(doctor.photo)
                tvDoctorNameItem.text = doctor.name
                tvSpecialistItem.text = doctor.specialist
                tvExperienceItem.text = itemView.context.getString(R.string.experience_value, doctor.experience)
                tvWorkingHourItem.text = doctor.workingTime
            }
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DoctorDetailActivity::class.java)
                intent.putExtra(DoctorDetailActivity.EXTRA_DOCTOR, doctor)
                itemView.context.startActivity(intent)
            }
        }
    }
}