//package com.tara.tv.presentation.home
//
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ImageView
//import android.widget.LinearLayout
//import android.widget.TextView
//import androidx.fragment.app.Fragment
//import androidx.lifecycle.ViewModelProvider
//import androidx.lifecycle.lifecycleScope
//import androidx.recyclerview.widget.DividerItemDecoration
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.ListAdapter
//import androidx.recyclerview.widget.DiffUtil
//import androidx.recyclerview.widget.RecyclerView
//import com.bumptech.glide.Glide
//import com.main.taratv.R
//
////import com.tara.tv.data.remote.ChannelDto
//
//import kotlinx.coroutines.flow.collectLatest
//import kotlinx.coroutines.launch
//
//class HomeFragment : Fragment() {
//
//    private lateinit var adapter: SimpleAdapter
//    private lateinit var vm: HomeViewModel
//
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        return inflater.inflate(R.layout.fragment_home, container, false)
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        // obtain ViewModel using a factory from ServiceLocator
////        vm = ViewModelProvider(this, com.tara.tv.di.ServiceLocator.provideHomeViewModelFactory())[HomeViewModel::class.java]
//
//        val recycler = view.findViewById<RecyclerView>(R.id.channels_recycler)
//        adapter = SimpleAdapter()
//        recycler.layoutManager = LinearLayoutManager(requireContext())
//        recycler.adapter = adapter
//        recycler.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
//
//        lifecycleScope.launch {
//            vm.channels.collectLatest { list ->
//                adapter.submitList(list)
//            }
//        }
//    }
//
//    class SimpleAdapter : ListAdapter<ChannelDto, SimpleAdapter.Holder>(object : DiffUtil.ItemCallback<ChannelDto>() {
//        override fun areItemsTheSame(oldItem: ChannelDto, newItem: ChannelDto) = oldItem.id == newItem.id
//        override fun areContentsTheSame(oldItem: ChannelDto, newItem: ChannelDto) = oldItem == newItem
//    }) {
//        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
//            val ctx = parent.context
//            val container = LinearLayout(ctx).apply {
//                orientation = LinearLayout.HORIZONTAL
//                setPadding(20,20,20,20)
//                layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
//                background = ctx.getDrawable(R.drawable.card_bg)
//                clipToPadding = false
//            }
//            val img = ImageView(ctx).apply {
//                layoutParams = ViewGroup.LayoutParams(96,96)
//                scaleType = ImageView.ScaleType.CENTER_CROP
//            }
//            val title = TextView(ctx).apply {
//                layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
//                textSize = 16f
//                setPadding(24, 0, 0, 0)
//            }
//            container.addView(img)
//            container.addView(title)
//            return Holder(container, img, title)
//        }
//
//        override fun onBindViewHolder(holder: Holder, position: Int) {
//            val item = getItem(position)
//            holder.title.text = item.title
//            val url = item.logoUrl
//            if (!url.isNullOrEmpty()) {
//                Glide.with(holder.image.context)
//                    .load(url)
//                    .placeholder(android.R.drawable.sym_def_app_icon)
//                    .error(android.R.drawable.sym_def_app_icon)
//                    .into(holder.image)
//            } else {
//                holder.image.setImageResource(android.R.drawable.sym_def_app_icon)
//            }
//        }
//
//        class Holder(view: View, val image: ImageView, val title: TextView) : RecyclerView.ViewHolder(view)
//    }
//}
