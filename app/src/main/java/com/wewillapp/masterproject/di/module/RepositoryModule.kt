package com.wewillapp.masterproject.di.module

import com.wewillapp.masterproject.data.rest.repository.GeneralRepository
import org.koin.dsl.module

val repositoryModule = module {

    single { GeneralRepository(get(), get()) }

}