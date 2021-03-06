package com.example.recetas.cocteleria

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recetas.R
import com.example.recetas.Recetas
import com.example.recetas.databinding.CocinaListBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.item_list.view.*

class RecetasCocteleriaList : Fragment() {

    private lateinit var binding: CocinaListBinding

    private val database = Firebase.database
    private lateinit var messagesListener: ValueEventListener
    private val listRecetas: MutableList<Recetas> = ArrayList()
    val myRef = database.getReference("Cocteleria")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CocinaListBinding.inflate(inflater)

        binding.imageReferencia.setImageResource(R.drawable.ic_aperitivos)
        binding.textViewReferencia.setText("Recetas de Coctelería")

        binding.floatingButton.setOnClickListener { v ->
            val intent = Intent(context, AddCocteleria::class.java)
            v.context.startActivity(intent)
        }

        listRecetas.clear()
        setupRecyclerView(binding.videogameRecyclerView)

        return binding.root
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {

        messagesListener = object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                listRecetas.clear()
                dataSnapshot.children.forEach { child ->
                    val recetas: Recetas? =
                        Recetas(
                            child.child("name").getValue<String>(),
                            child.child("date").getValue<String>(),
                            child.child("description").getValue<String>(),
                            child.child("url").getValue<String>(),
                            child.child("subidoPor").getValue<String>(),
                            child.child("pdf").getValue<String>(),
                            child.child("video").getValue<String>(),
                            child.child("word").getValue<String>(),
                            child.key
                        )
                    recetas?.let { listRecetas.add(it) }
                }
                recyclerView.adapter = RecetasCocteleriaAdapter(listRecetas)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("TAG", "messages:onCancelled: ${error.message}")
            }
        }
        myRef.addValueEventListener(messagesListener)

        deleteSwipe(recyclerView)
    }

    class RecetasCocteleriaAdapter(private val values: List<Recetas>) :
        RecyclerView.Adapter<RecetasCocteleriaAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_list, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val recetas = values[position]
            holder.mNameTextView.text = recetas.name
            holder.subidoPor.text = recetas.SubidoPor
            holder.mPosterImgeView?.let {
                Glide.with(holder.itemView.context)
                    .load(recetas.url)
                    .into(it)
            }

            holder.itemView.setOnClickListener { v ->
                val intent = Intent(v.context, DetailCocteleria::class.java).apply {
                    putExtra("key", recetas.key)
                }
                v.context.startActivity(intent)
            }

            holder.itemView.setOnLongClickListener { v ->
                val intent = Intent(v.context, EditCocteleria::class.java).apply {
                    putExtra("key", recetas.key)
                }
                v.context.startActivity(intent)
                true
            }

        }

        override fun getItemCount() = values.size

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val mNameTextView: TextView = view.tv_nameAdd
            val subidoPor: TextView = view.subidoPor
            val mPosterImgeView: ImageView? = view.posterImgeView
        }
    }

    private fun deleteSwipe(recyclerView: RecyclerView) {
        val touchHelperCallback: ItemTouchHelper.SimpleCallback = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                listRecetas.get(viewHolder.adapterPosition).key?.let {
                    myRef.child(it).setValue(null)
                }
                listRecetas.removeAt(viewHolder.adapterPosition)
                recyclerView.adapter?.notifyItemRemoved(viewHolder.adapterPosition)
                recyclerView.adapter?.notifyDataSetChanged()
            }
        }
        val itemTouchHelper = ItemTouchHelper(touchHelperCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

}

