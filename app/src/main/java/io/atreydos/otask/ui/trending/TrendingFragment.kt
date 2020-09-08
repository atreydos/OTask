package io.atreydos.otask.ui.trending

import android.os.Bundle
import android.view.View
import android.view.View.VISIBLE
import android.view.View.GONE
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import io.atreydos.otask.R
import io.atreydos.otask.domain.entity.TrendDetailed
import io.atreydos.otask.ui.widget.TrendDetailedText
import io.atreydos.otask.ui.widget.TrendDetailedWebView
import kotlinx.android.synthetic.main.trending_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class TrendingFragment : Fragment(R.layout.trending_fragment) {

    private val viewModel: TrendingViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews()
        observeLiveData()
    }

    private fun initViews() {
        btnNext.setOnClickListener { viewModel.navigateToNext() }
    }

    private fun observeLiveData() {
        viewModel.viewStateLD.observe(viewLifecycleOwner, Observer { viewState ->
            setProgressVisibility(viewState.isNetworking)
            setInputsEnabled(!viewState.isNetworking)
            viewState.errorMessage?.let { showErrorDialog(it) }
            viewState.trendDetailed?.let { showTrendDetailed(it) }
        })
    }

    private fun setProgressVisibility(isVisible: Boolean) {
        progressBar.visibility = if (isVisible) VISIBLE else GONE
    }

    private fun setInputsEnabled(isEnabled: Boolean) {
        btnNext.isEnabled = isEnabled
    }

    private fun showTrendDetailed(trendDetailed: TrendDetailed) {
        when (trendDetailed) {
            is TrendDetailed.Text -> TrendDetailedText(requireContext(), trendDetailed.content)
            is TrendDetailed.WebView -> TrendDetailedWebView(requireContext(), trendDetailed.url)
            else -> null //неизвестный объект просто не будет нарисован, а предыдущий не будет удалён
        }?.let { trendDetailedView ->
            frameLayout.removeAllViews()
            frameLayout.addView(trendDetailedView)
        }
    }

    private fun showErrorDialog(message: String) {
        AlertDialog.Builder(requireContext())
            .setIcon(R.drawable.ic_round_error_outline_24)
            .setTitle(R.string.error_dialog_title)
            .setMessage(message)
            .setCancelable(false)
            .setPositiveButton(R.string.btn_try_again) { dialog, _ ->
                dialog.dismiss()
                viewModel.tryAgain()
            }
            .show()
    }

}