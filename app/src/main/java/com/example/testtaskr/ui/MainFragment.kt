package com.example.testtaskr.ui
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testtaskr.adapters.PagingTopPostsAdapter
import com.example.testtaskr.databinding.FragmentMainBinding
import com.example.testtaskr.utils.Resource
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment() {


    private var _binding: FragmentMainBinding? = null
    val TAG = "MainFragmentTag"

    private val mViewModel: MainViewModel by viewModel()
    private lateinit var mPostsAdapter: PagingTopPostsAdapter
    private val bdn get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return bdn.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setObserverWithFlow(bdn)
        setObserverWithLivaData(bdn.root)
    }


    private fun setObserverWithFlow(binding: FragmentMainBinding) {
        mPostsAdapter = PagingTopPostsAdapter(binding, layoutInflater, childFragmentManager)

        bdn.mainRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = mPostsAdapter
        }
        this.lifecycleScope.launch {
            mViewModel.allTopPostsPaging.collectLatest {
                bdn.mainProgressBar.visibility = ProgressBar.VISIBLE
                mPostsAdapter.submitData(it)
            }
        }
    }
    private fun setObserverWithLivaData(view: RelativeLayout) {
        mViewModel.allPostsStatus.observe(viewLifecycleOwner, {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                }
                Resource.Status.ERROR -> {
                    Snackbar.make(
                        view,
                        it.message.toString(),
                        Snackbar.LENGTH_LONG
                    ).show()
                    Log.d(TAG, "Error")
                }
                Resource.Status.LOADING -> {
                    Log.d(TAG, "Loading")
                }
            }
        })
    }

}