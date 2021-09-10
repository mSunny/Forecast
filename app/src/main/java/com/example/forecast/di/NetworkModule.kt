package com.example.forecast.di

import com.example.forecast.data.db.LocationDao
import com.example.forecast.data.db.LocationRepository
import com.example.forecast.data.db.LocationRepositoryImpl
import com.example.forecast.data.network.ForecastRepository
import com.example.forecast.data.network.ForecastRepositoryImpl
import com.example.forecast.data.network.GeocodingApiService
import com.example.forecast.data.network.OpenWeatherApiService
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    @RetrofitForForecastService
    fun provideRetrofitForForecast(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    @RetrofitForGeocodingService
    fun provideRetrofitForGeoCoding(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.openweathermap.org/geo/1.0/")
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        interceptors: ArrayList<Interceptor>
    ): OkHttpClient {
        val clientBuilder = OkHttpClient.Builder()
            .followRedirects(false)
        interceptors.forEach {
            clientBuilder.addInterceptor(it)
        }
        return clientBuilder.build()
    }


    @Singleton
    @Provides
    fun provideInterceptors(): ArrayList<Interceptor> {
        val interceptors = arrayListOf<Interceptor>()
        val loggingInterceptor = HttpLoggingInterceptor()
            .apply {level = HttpLoggingInterceptor.Level.BODY}
        interceptors.add(loggingInterceptor)
        return interceptors
    }

    @Singleton
    @Provides
    fun provideOpenWeatherApiService(@RetrofitForForecastService retrofit: Retrofit): OpenWeatherApiService {
        return retrofit.create(OpenWeatherApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideGeocodingApiService(@RetrofitForGeocodingService retrofit: Retrofit): GeocodingApiService {
        return retrofit.create(GeocodingApiService::class.java)
    }

    @Singleton
    @Provides
    fun forecastRepository(openWeatherApiService: OpenWeatherApiService, geocodingApiService: GeocodingApiService): ForecastRepository {
        return ForecastRepositoryImpl(openWeatherApiService, geocodingApiService)
    }


    @Retention(AnnotationRetention.BINARY)
    @Qualifier
    annotation class RetrofitForForecastService

    @Retention(AnnotationRetention.BINARY)
    @Qualifier
    annotation class RetrofitForGeocodingService
}