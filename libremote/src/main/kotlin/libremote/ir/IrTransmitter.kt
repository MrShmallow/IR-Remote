package libremote.ir

import libremote.core.Transmitter

class IrTransmitter(
    private val carrierFrequency: Int,
    private val pulseBurstLength: Int,
    private val transmit: (carrierFrequency: Int, pattern: Iterable<Int>) -> Unit
) : Transmitter {
    override fun transmit(data: Iterable<Int>) {
        transmit(carrierFrequency, data.map { it * pulseBurstLength })
    }
}