package libremote.ir

import libremote.core.Transmitter

/**
 * A signature for functions which transmit IR data.
 * This is based on the signature of ConsumerIrManager.transmit() from the Android API.
 *
 * The method will get the following parameters:
 *
 * carrierFrequency - The IR carrier frequency in Hertz.
 *
 * pattern - The alternating on/off pattern in microseconds to transmit.
 */
typealias IrTransmitFunction = (carrierFrequency: Int, pattern: Iterable<Int>) -> Unit

/**
 * A generic transmitter for infra-red data.
 *
 * @property carrierFrequency The IR carrier frequency in Hertz.
 * @property pulseBurstLength Length of a pulse burst in microseconds. This is the granularity for
 * transmissions - the data is specified as a list of on/off bursts with this length multiplied by
 * each value.
 * @property transmit The actual implementation for the transmission function.
 *
 * @author Ofir Sela
 * @version 1.0.0
 */
class IrTransmitter(
    private val carrierFrequency: Int,
    private val pulseBurstLength: Double,
    private val transmit: IrTransmitFunction
) : Transmitter<Iterable<Int>> {
    /**
     * Transmits the given pulse bursts list.
     *
     * @param data The alternating on/off pulse bursts.
     *
     * @see [Transmitter.transmit]
     */
    override fun transmit(data: Iterable<Int>) {
        transmit(carrierFrequency, data.map { (it * pulseBurstLength).toInt() })
    }
}