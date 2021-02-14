package com.mycodeflow.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mycodeflow.ui.R
import com.mycodeflow.data.Actor

class DetailCastListAdapter(
    private val actors: List<Actor>?
): RecyclerView.Adapter<ActorViewHolder>() {

    private fun getItem(position: Int) = actors?.get(position)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorViewHolder {
        return ActorViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_holder_actor, parent, false))
    }

    override fun onBindViewHolder(holder: ActorViewHolder, position: Int) {
        getItem(position)?.let { holder.onBind(it) }
    }

    override fun getItemCount(): Int = actors?.size ?: 0
}

class ActorViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    private val portrait: ImageView = itemView.findViewById(R.id.actor_photo)
    private val name: TextView = itemView.findViewById(R.id.actor_name)

    fun onBind(actor: Actor){
        Glide.with(itemView.context)
            .load(actor.profilePicture)
            .placeholder(R.drawable.no_avatar)
            .into(portrait)
        name.text = actor.name
    }
}
