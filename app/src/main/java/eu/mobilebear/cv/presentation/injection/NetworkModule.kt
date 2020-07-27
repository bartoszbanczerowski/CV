package eu.mobilebear.cv.presentation.injection

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import eu.mobilebear.cv.BuildConfig
import eu.mobilebear.cv.networking.CVService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    factory { getLoggingInterceptor() }
    factory { provideHeaderInterceptor() }
    factory { provideOkHttpClient(get(), get()) }
    factory { provideMoshiBuilder() }
    single { provideRetrofit(get(), get()) }
    single { provideCVService(get()) }
}

private const val REQUEST_HEADER_USER_AGENT_KEY = "User-Agent"
private const val REQUEST_HEADER_USER_AGENT_VALUE = "Android"
private const val REQUEST_HEADER_ACCEPT = "Content-type"
private const val REQUEST_HEADER_CONTENT_TYPE = "Content-type"
private const val REQUEST_HEADER_APPLICATION_JSON = "application/json"
private const val HOST = "https://gist.githubusercontent.com/bartoszbanczerowski/c8cf4c704bef2066cec059e4318f9d84/raw/fe56b2941b37fdf1ea73d8c1a78312e33731dc3c/"
private const val CONNECT_TIMEOUT: Long = 60
private const val READ_TIMEOUT: Long = 60
private const val WRITE_TIMEOUT: Long = 60

private fun getLoggingInterceptor(): HttpLoggingInterceptor {
    val loggingInterceptor = HttpLoggingInterceptor()
    loggingInterceptor.level =
        if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BASIC else HttpLoggingInterceptor.Level.NONE
    return loggingInterceptor
}

private fun provideHeaderInterceptor(): Interceptor = Interceptor { chain ->
    val originalRequest = chain.request()
    val request = originalRequest.newBuilder()
        .header(REQUEST_HEADER_USER_AGENT_KEY, REQUEST_HEADER_USER_AGENT_VALUE)
        .header(REQUEST_HEADER_ACCEPT, REQUEST_HEADER_APPLICATION_JSON)
        .header(REQUEST_HEADER_CONTENT_TYPE, REQUEST_HEADER_APPLICATION_JSON)
        .build()
    chain.proceed(request)
}

private fun provideOkHttpClient(
    loggingInterceptor: HttpLoggingInterceptor,
    headerInterceptor: Interceptor
): OkHttpClient =
    OkHttpClient.Builder()
        .addInterceptor(headerInterceptor)
        .addNetworkInterceptor(loggingInterceptor)
        .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
        .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
        .build()

private fun provideMoshiBuilder(): Moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private fun provideRetrofit(moshi: Moshi, okHttpClient: OkHttpClient): Retrofit =
    Retrofit.Builder()
        .baseUrl(HOST)
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

private fun provideCVService(retrofit: Retrofit): CVService = retrofit.create(CVService::class.java)