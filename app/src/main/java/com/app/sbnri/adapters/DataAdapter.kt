package com.app.sbnri.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.sbnri.R
import com.app.sbnri.data.models.GitDataItem
import kotlinx.android.synthetic.main.rv_list_item.view.*

class DataAdapter() : RecyclerView.Adapter<DataAdapter.DataViewHolder>() {

    inner class DataViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(data: GitDataItem) {
            itemView.apply {
                tvName.text = data?.name
                tvDescription.text = data.description
                tvOpenAccountIssues.text = "Open Issuesa: ${data.open_issues_count}"
                tvLicense.text = "License: ${data.license?.name}"

                if (data.permissions?.admin!!) {
                    tvAdmin.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.admin_blue, 0, 0)
                    tvAdmin.setBackgroundResource(0)
                    tvAdmin.setBackgroundResource(R.drawable.selectbackground)
                }

                if (data.permissions?.push!!) {
                    tvPush.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.ic_arrow_blue, 0, 0)
                    tvPush.setBackgroundResource(0)
                    tvPush.setBackgroundResource(R.drawable.selectbackground)
                }

                if (data.permissions?.pull!!) {
                    tvPull.setCompoundDrawablesWithIntrinsicBounds(0,R.drawable.ic_arrow_down_black, 0, 0)
                    tvPull.setBackgroundResource(0)
                    tvPull.setBackgroundResource(R.drawable.selectbackground)
                }

            }
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<GitDataItem>() {
        override fun areItemsTheSame(oldItem: GitDataItem, newItem: GitDataItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: GitDataItem, newItem: GitDataItem): Boolean {
            return oldItem == newItem
        }
    }

    var differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataAdapter.DataViewHolder {
        return DataViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.rv_list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}