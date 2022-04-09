package libremote.core

interface Transmitter {
    fun transmit(data: Iterable<Int>)
}