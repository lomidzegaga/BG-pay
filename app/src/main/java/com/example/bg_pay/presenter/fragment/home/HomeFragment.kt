package com.example.bg_pay.presenter.fragment.home

import androidx.fragment.app.viewModels
import com.example.bg_pay.common.BaseFragment
import com.example.bg_pay.databinding.FragmentHomeBinding
import com.google.android.material.snackbar.Snackbar

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel: HomeViewModel by viewModels()

    override fun listeners() {
        binding.btnPay.setOnClickListener {
            viewModel.getMoney()
            viewModel.updateMoney(
                newMoney = binding.etMoney.text.toString().toFloat(),
            )
            binding.etMoney.setText("")
            Snackbar.make(binding.root, "payment is successful", Snackbar.LENGTH_LONG)
                .show()
        }

    }

    override fun init() {}

    override fun observers() {}
}