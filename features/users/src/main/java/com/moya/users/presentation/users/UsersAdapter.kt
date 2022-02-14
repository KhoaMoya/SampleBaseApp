package com.moya.users.presentation.users

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.moya.users.databinding.ItemUserBinding
import com.moya.users.presentation.model.UiUser

class UsersAdapter(private val listener: (Int) -> Unit) :
    RecyclerView.Adapter<UsersAdapter.UserViewHolder>() {

    private val _users: MutableList<UiUser> = mutableListOf()

    fun setUsers(users: List<UiUser>) {
        _users.clear()
        _users.addAll(users)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            ItemUserBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), listener
        )
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(_users[position])
    }

    override fun getItemCount(): Int = _users.size

    class UserViewHolder(
        private val binding: ItemUserBinding,
        private val listener: (Int) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {

        private var _user: UiUser? = null

        init {
            binding.root.setOnClickListener {
                _user?.let { listener.invoke(it.id) }
            }
        }

        fun bind(uiUser: UiUser) {
            _user = uiUser
            binding.run {
//                ivAvatar.loadImage(uiUser.avatar)
                tvUserName.text = uiUser.name
                tvLocation.text = uiUser.location
            }
        }

    }
}