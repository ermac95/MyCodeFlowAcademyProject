package com.mycodeflow.movieadapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mycodeflow.academyproject.R
import com.mycodeflow.datamodel.Actor

class DetailCastListAdapter(
    context: Context,
    private val actors: List<Actor>
): RecyclerView.Adapter<ActorViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    private fun getItem(position: Int) = actors[position]

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorViewHolder {
        return ActorViewHolder(inflater.inflate(R.layout.view_holder_actor, parent, false))
    }

    override fun onBindViewHolder(holder: ActorViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    override fun getItemCount(): Int = actors.size
}

class ActorViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    private val portrait: ImageView = itemView.findViewById(R.id.actor_photo)
    private val name: TextView = itemView.findViewById(R.id.actor_name)

    fun onBind(actor: Actor){
        portrait.setImageResource(actor.portrait)
        name.text = actor.name
    }
}