package com.ctis487_fp.finalproject.customeradapter

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.ctis487_fp.finalproject.customer.CustomerMainPage
import com.ctis487_fp.finalproject.databinding.ItemMedicineBinding
import com.ctis487_fp.finalproject.model.Medicine
import com.ctis487_fp.finalproject.service.module.ItemSwipeListener

class MedicineAdapter(
    private var medicineList: List<Medicine>,
    private val pharmacyName: String,
    private val onLongPress: (Medicine, Int) -> Unit,
    private val itemSwipeListener: ItemSwipeListener,
    private val onItemSwiped: (Medicine, String) -> Unit
) : RecyclerView.Adapter<MedicineAdapter.MedicineViewHolder>() {

    private var recentlySwipedPosition: Int? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicineViewHolder {
        val binding = ItemMedicineBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MedicineViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MedicineViewHolder, position: Int) {
        val medicine = medicineList[position]
        holder.bind(medicine)
        holder.itemView.setOnLongClickListener {
            onLongPress(medicine, position)
            true
        }

        if (recentlySwipedPosition == position) {
            holder.itemView.translationX = 0f
        }
    }

    override fun getItemCount(): Int = medicineList.size

    inner class MedicineViewHolder(private val binding: ItemMedicineBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(medicine: Medicine) {
            binding.tvTitle.text = medicine.title
            binding.tvDescription.text = medicine.description
            binding.tvType.text = medicine.type.toString()
        }
    }

    fun attachItemTouchHelper(recyclerView: RecyclerView, customerMainPage: CustomerMainPage) {
        val paint = Paint()
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder) = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val medicine = medicineList[position]
                val action = if (direction == ItemTouchHelper.RIGHT) "increase" else "decrease"

                itemSwipeListener.onItemSwiped(action)

                onItemSwiped(medicine, action)

                recentlySwipedPosition = position

                val updatedList = medicineList.toMutableList()
                medicineList = updatedList
                notifyItemChanged(position)
            }

            override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
                val itemView = viewHolder.itemView
                paint.color = if (dX > 0) Color.GREEN else Color.RED
                c.drawRect(itemView.left.toFloat(), itemView.top.toFloat(), itemView.right.toFloat(), itemView.bottom.toFloat(), paint)

                if (isCurrentlyActive) {
                    itemView.translationX = dX
                }

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }
        }

        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView)
    }

    fun updateList(newList: List<Medicine>) {
        medicineList = newList
        notifyDataSetChanged()
    }
}
