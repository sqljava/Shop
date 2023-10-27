package uz.ictschool.shop.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import uz.ictschool.shop.R
import uz.ictschool.shop.SharedPrefHelper
import uz.ictschool.shop.adapters.ImageAdapter
import uz.ictschool.shop.databinding.FragmentProductInfoBinding
import uz.ictschool.shop.model.Product

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "product"

class ProductInfoFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var product: Product
    lateinit var binding: FragmentProductInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            product = it.getSerializable(ARG_PARAM1) as Product
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductInfoBinding.inflate(inflater, container, false)

        binding.productScreenBack.setOnClickListener {
            findNavController().navigate(R.id.action_productInfoFragment_to_homeFragment)
        }


        binding.titleProductInfo.text = product.title
        binding.brandProductInfo.text = product.brand
        binding.priceProductInfo.text = product.price.toString() + " $"
        binding.descriptionProductInfo.text = product.description
        binding.ratingProductInfo.text = product.rating.toString()


        binding.productViewpager.adapter = ImageAdapter(product.images,
            binding.productViewpager, binding.productInfoMain)


        binding.btnAddProductInfo.setOnClickListener {

            val shared = SharedPrefHelper.getInstance(requireContext())
            val bundle = Bundle()
            bundle.putSerializable("product", product)
            //bundle.putInt("quantity", quantity)
            if (shared.getUser() == null){
                findNavController().navigate(R.id.action_productInfoFragment_to_loginFragment, bundle)
            }else{
                //findNavController().navigate(R.id.action_productFragment_to_cartFragment, bundle)
            }

        }




        return binding.root
    }

}