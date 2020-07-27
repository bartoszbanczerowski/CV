package eu.mobilebear.cv.util

interface OnConnectionChangeListener {

    fun onConnectionLost()

    fun onConnectionReestablished()

    fun onConnectionStatusDetected(connected: Boolean)
}
