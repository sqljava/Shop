package uz.ictschool.shop.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.ictschool.shop.R
import uz.ictschool.shop.adapters.CategoryAdapter
import uz.ictschool.shop.adapters.ProductAdapter
import uz.ictschool.shop.api.APIClient
import uz.ictschool.shop.api.APIService
import uz.ictschool.shop.databinding.FragmentHomeBinding
import uz.ictschool.shop.model.Product
import uz.ictschool.shop.model.ProductData

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var binding: FragmentHomeBinding
    val api = APIClient.getInstance().create(APIService::class.java)
    lateinit var allProducts : List<Product>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)

        putAllProducts()
        putCategories()

        return binding.root
    }

    fun putCategories(){
        api.getCategories().enqueue(object : Callback<List<String>>{
            override fun onResponse(call: Call<List<String>>, response: Response<List<String>>) {
                var categories = response.body()!!
                binding.recCategoryHome.adapter = CategoryAdapter(requireContext(), categories,
                    object : CategoryAdapter.CategoryClicked{
                        override fun onClicked(category: String) {

                            if (category == ""){
                                changeProductAdapter(allProducts)
                            }else{
                                api.getByCategory(category).enqueue(object
                                    :Callback<ProductData>{
                                    override fun onResponse(
                                        call: Call<ProductData>,
                                        response: Response<ProductData>
                                    ) {
                                        var productsByCategory = response.body()!!.products
                                        changeProductAdapter(productsByCategory)
                                    }
                                    override fun onFailure(call: Call<ProductData>, t: Throwable) {
                                        Log.d("TAG", "onFailure: $t")
                                    }
                                })
                            }
                        }
                    })
            }

            override fun onFailure(call: Call<List<String>>, t: Throwable) {
                Log.d("TAG", "onFailure: $t")
            }

        })
    }

    fun putAllProducts(){
        api.getAllProducts().enqueue(object : Callback<ProductData>{
            override fun onResponse(call: Call<ProductData>, response: Response<ProductData>) {
                var products = response.body()!!.products
                allProducts = products
                binding.recProductsHome.adapter = ProductAdapter(products,
                    object :ProductAdapter.ProductClicked{
                        override fun onClicked(product: Product) {
                            val bundle = Bundle()
                            bundle.putSerializable("product", product)

                            findNavController().navigate(R.id
                                .action_homeFragment_to_productInfoFragment,
                                bundle)
                        }

                    })
                Log.d("TAG", "onResponse: $products")
            }

            override fun onFailure(call: Call<ProductData>, t: Throwable) {
                Log.d("TAG", "onFailure: $t")
            }
        })
    }

    fun changeProductAdapter(products: List<Product>){
        binding.recProductsHome.adapter = ProductAdapter(products,
            object : ProductAdapter.ProductClicked{
                override fun onClicked(product: Product) {
                    val bundle = Bundle()
                    bundle.putSerializable("product", product)

                    findNavController().navigate(R.id
                        .action_homeFragment_to_productInfoFragment,
                        bundle)
                }

            })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}