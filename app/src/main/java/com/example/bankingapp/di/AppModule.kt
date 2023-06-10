package com.example.bankingapp.di

import android.content.Context
import com.example.bankingapp.db.BankDatabase
import com.example.bankingapp.db.BankRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideBankRepository(db: BankDatabase): BankRepository {
        return BankRepository(db.bankDao())
    }

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context, scope: CoroutineScope): BankDatabase {
        return BankDatabase.getInstance(context, scope)
    }

    @Provides
    @Singleton
    fun providesApplicationScope() = CoroutineScope(SupervisorJob())
}