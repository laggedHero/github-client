package net.laggedhero.githubclient.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.jakewharton.rxbinding3.widget.afterTextChangeEvents
import io.reactivex.disposables.CompositeDisposable
import net.laggedhero.githubclient.databinding.FragmentSearchBinding
import net.laggedhero.githubclient.extensions.rx.plusAssign
import net.laggedhero.githubclient.platform.SchedulerProvider
import java.util.concurrent.TimeUnit

class SearchFragment(
    private val viewModelFactory: ViewModelProvider.Factory,
    private val schedulerProvider: SchedulerProvider
) : Fragment() {

    private val viewModel by viewModels<SearchViewModel> { viewModelFactory }

    private var _binding: FragmentSearchBinding? = null
    private val binding: FragmentSearchBinding
        get() = _binding!!

    private var compositeDisposable = CompositeDisposable()

    private val searchListAdapter = SearchListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        subscribeToUi()
        subscribeToViewModel()
    }

    private fun setupRecyclerView() {
        binding.fragmentSearchRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = searchListAdapter
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
    }

    private fun subscribeToUi() {
        compositeDisposable += binding.fragmentSearchEditText.afterTextChangeEvents()
            .subscribeOn(schedulerProvider.computation())
            .debounce(300, TimeUnit.MILLISECONDS)
            .filter { it.editable?.length ?: 0 >= 3 }
            .doOnNext { viewModel.search(it.editable.toString()) }
            .subscribe()
    }

    private fun subscribeToViewModel() {
        compositeDisposable += viewModel.state
            .observeOn(schedulerProvider.ui())
            .subscribe { state ->
                binding.fragmentSearchLoadingHolder.isVisible = state.loading
                state.repositoryList?.let { searchListAdapter.submitList(it) }
                state.error?.let { showError(it) }
            }
    }

    private fun showError(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        compositeDisposable.clear()
        super.onDestroyView()
    }
}