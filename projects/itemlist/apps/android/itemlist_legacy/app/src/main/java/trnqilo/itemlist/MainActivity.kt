package trnqilo.itemlist

import android.R.id.text1
import android.R.layout.simple_list_item_1
import android.os.Bundle
import android.view.LayoutInflater.from
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat.setOnApplyWindowInsetsListener
import androidx.core.view.WindowInsetsCompat.Type.systemBars
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import trnqilo.itemlist.databinding.ActivityMainBinding.inflate

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    viewModels<ItemsViewModel>().value.let { viewModel ->
      inflate(layoutInflater).apply {
        setContentView(root)
        setOnApplyWindowInsetsListener(root) { view, insets ->
          insets.apply {
            getInsets(systemBars()).apply { view.setPadding(left, top, right, bottom) }
          }
        }
        button.setOnClickListener { viewModel.addItem(editText.text.toString()) }
        ItemsAdapter { position -> viewModel.removeItem(position) }.apply {
          list.adapter = this
          viewModel.items().observe(this@MainActivity) { item -> submitList(item) }
        }
      }
    }
  }
}

class ItemsViewModel : ViewModel() {
  private val itemsData: MutableLiveData<List<String>> = MutableLiveData(emptyList())

  fun items(): LiveData<List<String>> = itemsData

  fun addItem(item: String) {
    if (item.isNotBlank()) {
      itemsData.value?.toMutableList()?.apply {
        add(item)
        itemsData.value = this
      }
    }
  }

  fun removeItem(index: Int) {
    itemsData.value?.toMutableList()?.apply {
      if (index in 0 until size) {
        removeAt(index)
        itemsData.value = this
      }
    }
  }
}

class ItemsAdapter(private val onClick: (Int) -> Unit) :
  ListAdapter<String, ItemViewHolder>(ItemsDiffUtil) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder =
    ItemViewHolder(from(parent.context).inflate(simple_list_item_1, parent, false))

  override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
    holder.apply {
      bind(getItem(position))
      itemView.setOnClickListener { onClick(adapterPosition) }
    }
  }
}

class ItemViewHolder(private val view: View) :
  ViewHolder(view) {
  fun bind(item: String) {
    view.findViewById<TextView>(text1).text = item
  }
}

object ItemsDiffUtil : DiffUtil.ItemCallback<String>() {
  override fun areItemsTheSame(oldItem: String, newItem: String): Boolean = oldItem == newItem
  override fun areContentsTheSame(oldItem: String, newItem: String): Boolean = oldItem == newItem
}