package com.wewillapp.masterproject.di.module

import com.wewillapp.masterproject.data.rest.repository.GeneralRepository
import com.wewillapp.masterproject.domain.GeneralUseCase
import org.koin.dsl.module

val repositoryModule = module {

    single { GeneralRepository(get()) }

    single { GeneralUseCase(get(), get()) }

}