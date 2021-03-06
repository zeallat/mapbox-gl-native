package com.mapbox.mapboxsdk.testapp.maps

import android.support.test.espresso.UiController
import android.support.test.runner.AndroidJUnit4
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.style.layers.SymbolLayer
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource
import com.mapbox.mapboxsdk.testapp.action.MapboxMapAction
import com.mapbox.mapboxsdk.testapp.activity.BaseActivityTest
import com.mapbox.mapboxsdk.testapp.activity.espresso.EspressoTestActivity
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class StyleLoadTest : BaseActivityTest() {

    private lateinit var mapView: MapView

    override fun getActivityClass(): Class<*> {
        return EspressoTestActivity::class.java
    }

    @Before
    override fun beforeTest() {
        super.beforeTest()
        mapView = (rule.activity as EspressoTestActivity).mapView
    }

    @Test
    fun updateSourceAfterStyleLoad() {
        validateTestSetup()
        MapboxMapAction.invoke(mapboxMap) { uiController: UiController, mapboxMap: MapboxMap ->
            val source = GeoJsonSource("id")
            val layer = SymbolLayer("id", "id")
            mapboxMap.setStyle(Style.Builder().withSource(source).withLayer(layer))
            uiController.loopMainThreadForAtLeast(100)
            mapboxMap.setStyle(Style.Builder().fromUrl(Style.MAPBOX_STREETS))
            uiController.loopMainThreadForAtLeast(100)
            source.setGeoJson("{}")
        }
    }
}