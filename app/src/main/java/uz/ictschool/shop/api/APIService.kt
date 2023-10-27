package uz.ictschool.shop.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import uz.ictschool.shop.model.Login
import uz.ictschool.shop.model.ProductData
import uz.ictschool.shop.model.User

interface APIService {

    @GET("/products")
    fun getAllProducts(): Call<ProductData>

    @GET("/products/categories")
    fun getCategories(): Call<List<String>>

    @GET("/products/category/{category}")
    fun getByCategory(@Path("category") category : String): Call<ProductData>

    @POST("/auth/login")
    fun login(@Body login: Login): Call<User>



}