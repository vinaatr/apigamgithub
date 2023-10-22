package com.example.apigram2

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.apigram2.data.model.ResponseUser
import com.example.apigram2.databinding.ItemRowUserBinding

class UserAdapter(
    private val data: MutableList<ResponseUser.Item> = mutableListOf(),
    private val listener: (ResponseUser.Item) -> Unit
) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    fun setData(data: MutableList<ResponseUser.Item>) {
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    class UserViewHolder(private val v: ItemRowUserBinding) : RecyclerView.ViewHolder(v.root) {
        fun bind(item: ResponseUser.Item) {
            v.imageUser.load(item.avatar_url)
            v.username.text = item.login
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder =
        UserViewHolder(ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            listener(item)
        }
    }

    override fun getItemCount(): Int = data.size
}