package mx.com.emv.menotes.data.remote.service

import mx.com.emv.menotes.data.remote.NoteDTO
import mx.com.emv.menotes.data.remote.response.NotesResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

object NotesApiClient {
    private const val URL = "http://5.5.5.26/AndroidApps/APIS/MeNotes/v1/"
    private lateinit var serviceApi: ServiceApi

    private val interceptor = HttpLoggingInterceptor().apply {
        this.level = HttpLoggingInterceptor.Level.BODY
    }

    fun build(): ServiceApi {
        val builder = Retrofit.Builder().apply {
            this.baseUrl(URL)
            this.addConverterFactory(GsonConverterFactory.create())
        }

        //TODO development
        val httpClient = OkHttpClient.Builder().let {
            it.addInterceptor(interceptor)
            it.build()
        }

        val retrofit = builder.let {
            it.client(httpClient)
            it.build()
        }
        serviceApi = retrofit.create(ServiceApi::class.java)

        return serviceApi
    }

    interface ServiceApi {
        @GET("notes")
        suspend fun getAll(): Response<NotesResponse>

        @GET("notes/{id}")
        suspend fun getNote(@Path("id") id: Int): Response<NoteDTO>
    }
}