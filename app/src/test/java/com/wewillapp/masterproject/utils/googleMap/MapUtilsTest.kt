package com.wewillapp.masterproject.utils.googleMap

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.wewillapp.masterproject.base.BaseUTTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.not
import org.hamcrest.Matchers.isEmptyString
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.parameter.parametersOf
import org.koin.test.inject
import org.robolectric.annotation.Config


@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.P])
class MapUtilsTest : BaseUTTest() {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private val mapUtils: MapUtils by inject()

    @Test
    fun getLocationEmpty_ReturnLocationEmpty() {
        val result =
            mapUtils.getLocaltionAddress( 0.0, 0.0)

        ViewMatchers.assertThat(result, `is`("ไม่พบตำแหน่ง"))
    }

    @Test
    fun getLocationCompleted_ReturnLocation() {
        val result =
            mapUtils.getLocaltionAddress( 13.765096, 100.538306)

        ViewMatchers.assertThat(result, not(isEmptyString()))
    }

    @Test
    fun getStringFormat_ReturnMillimeter(){
        val distance = 0.0
        val result = mapUtils.formatNumbers(distance)

        ViewMatchers.assertThat(result, `is`(String.format("%4.2f%s", distance, " MM.")))
    }

    @Test
    fun getStringFormat_ReturnKiloMeter(){
        val distance = 2000.0
        val result = mapUtils.formatNumbers(distance)

        ViewMatchers.assertThat(result, `is`(String.format("%4.2f%s", 2.00, " KM.")))
    }

    @Test
    fun getStringFormat_ReturnNotCase(){
        val distance = 1000.0
        val result = mapUtils.formatNumbers(distance)

        ViewMatchers.assertThat(result, `is`(String.format("%4.2f%s", distance, " M.")))
    }
}