package miki.learn.finglance

import android.app.Application
import miki.learn.finglance.ui.container.AlphaVantageContainer
import miki.learn.finglance.ui.container.StockContainer

class FinGlanceApplication : Application() {
    lateinit var container: StockContainer
    override fun onCreate() {
        super.onCreate()
        container = AlphaVantageContainer(this)
    }
}