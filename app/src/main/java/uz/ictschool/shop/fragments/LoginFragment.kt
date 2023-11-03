package uz.ictschool.shop.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.ictschool.shop.R
import uz.ictschool.shop.SharedPrefHelper
import uz.ictschool.shop.api.APIClient
import uz.ictschool.shop.api.APIService
import uz.ictschool.shop.databinding.FragmentLoginBinding
import uz.ictschool.shop.model.Login
import uz.ictschool.shop.model.Product
import uz.ictschool.shop.model.User

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var product: Product? = null
    lateinit var binding: FragmentLoginBinding
    val api = APIClient.getInstance().create(APIService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            product = it.getSerializable("product") as Product
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        loginClicked()


        return binding.root
    }

    fun loginClicked(){
        binding.btnLogin.setOnClickListener {
            var username = binding.loginUsername.text.toString()
            var password = binding.loginPassword.text.toString()

            api.login(Login(username, password)).enqueue(object : Callback<User>{
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if (!response.isSuccessful) {
                        Toast.makeText(requireContext(), "Incorrect login or password", Toast.LENGTH_SHORT).show()
                        binding.loginPassword.setText("")
                        return
                    }
                    val shared = SharedPrefHelper.getInstance(requireContext())
                    val user = response.body()!!
                    shared.setUser(user)

                    val bundle = Bundle()
                    bundle.putSerializable("product", product)

                    findNavController().navigate(R.id.action_loginFragment_to_cartFragment, bundle)
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    Log.d("TAG", "onFailure: $t")
                }

            })



        }
    }



}