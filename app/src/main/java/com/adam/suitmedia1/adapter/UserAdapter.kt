package com.adam.suitmedia1.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.adam.suitmedia1.R
import com.adam.suitmedia1.data.response.User
import com.bumptech.glide.Glide

class UserAdapter(private val userList: List<User>) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private var onItemClickListener: ((User) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.user_row, parent, false)
        return UserViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentUser = userList[position]
        holder.bind(currentUser)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    fun setOnItemClickListener(listener: (User) -> Unit) {
        onItemClickListener = listener
    }

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val circleImageView: ImageView = itemView.findViewById(R.id.circleImageView)
        private val firstnameTextView: TextView = itemView.findViewById(R.id.tv_firstname)
        private val lastnameTextView: TextView = itemView.findViewById(R.id.tv_lastname)
        private val emailTextView: TextView = itemView.findViewById(R.id.email)

        fun bind(user: User) {
            // Set the user data to the views
            Glide.with(itemView)
                .load(user.avatar)
                .centerCrop()
                .into(circleImageView)
            firstnameTextView.text = user.firstName
            lastnameTextView.text = user.lastName
            emailTextView.text = user.email

            itemView.setOnClickListener {
                onItemClickListener?.invoke(user)
            }
        }
    }
}