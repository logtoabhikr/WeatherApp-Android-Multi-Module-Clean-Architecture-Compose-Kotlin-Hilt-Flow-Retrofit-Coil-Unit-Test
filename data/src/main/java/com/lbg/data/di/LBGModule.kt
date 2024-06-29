package com.lbg.data.di

import com.google.gson.GsonBuilder
import com.lbg.data.network.LBGService
import com.lbg.data.utils.BaseURL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LBGModule {
    @Provides
    @Singleton
    fun provideLoggerInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.apply { interceptor.level = HttpLoggingInterceptor.Level.HEADERS }
        interceptor.apply { interceptor.level = HttpLoggingInterceptor.Level.BODY }
        return interceptor
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggerInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient {
        val timeOut = 30
        val httpClient = OkHttpClient().newBuilder()
            .connectTimeout(timeOut.toLong(), TimeUnit.SECONDS)
            .readTimeout(timeOut.toLong(), TimeUnit.SECONDS)
            .writeTimeout(timeOut.toLong(), TimeUnit.SECONDS)

        httpClient.addInterceptor(loggerInterceptor)
        httpClient.addInterceptor { chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder()
                .addHeader("Accept", "application/json")
                .addHeader("AppId", "1")
                .addHeader("Platform", "Android")
            val request = requestBuilder.build()
            chain.proceed(request)
        }
        return httpClient.build()
    }

    @Provides
    @Singleton
    fun provideLBGService(
        gsonConverterFactory: GsonConverterFactory,
        okHttpClient: OkHttpClient
    ): LBGService {
        return Retrofit.Builder()
            .baseUrl(BaseURL.URL)
            .addConverterFactory(gsonConverterFactory)
            .client(okHttpClient)
            .build()
            .create(LBGService::class.java)
    }

    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory {
        val gson = GsonBuilder().enableComplexMapKeySerialization()
            .serializeNulls()
            .setPrettyPrinting()
            .setLenient().create()
        return GsonConverterFactory.create(gson)
    }
}