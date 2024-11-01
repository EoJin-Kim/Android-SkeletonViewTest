package com.ej.skeletonviewtest

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.ej.skeletonviewtest.databinding.ViewTemplateSkeletonBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SkeletonTemplateView : LinearLayout{

    private lateinit var viewBinding: ViewTemplateSkeletonBinding


    constructor(context: Context) : super(context) {
        viewBinding = ViewTemplateSkeletonBinding.inflate(LayoutInflater.from(context), this, true)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        viewBinding = ViewTemplateSkeletonBinding.inflate(LayoutInflater.from(context), this, true)
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.SkeletonTemplateView)
            try {
                val itemId = typedArray.getResourceId(R.styleable.SkeletonTemplateView_skt_item_id, R.layout.item_skeleton_basic)
                val listStatus = typedArray.getBoolean(R.styleable.SkeletonTemplateView_skt_list, false)
                val layoutInflater = LayoutInflater.from(context)

                if (listStatus) {
                    for (i in 0..10) {
                        val itemView =
                            layoutInflater.inflate(itemId, viewBinding.llSkeletonItem, false)
                        viewBinding.llSkeletonItem.addView(itemView)
                    }
                } else {
                    val itemView =
                        layoutInflater.inflate(itemId, viewBinding.llSkeletonItem, false)
                    viewBinding.llSkeletonItem.addView(itemView)
                }
            } finally {
                typedArray.recycle()
            }
        }
    }

    fun setLoading(status : Boolean){
        CoroutineScope(Dispatchers.Main).launch {
            if (status) {
                viewBinding.shimmerFrameLayout.startShimmer()
                visibility = View.VISIBLE
            } else {
                viewBinding.shimmerFrameLayout.stopShimmer()
                visibility = View.GONE
            }
        }
    }
}