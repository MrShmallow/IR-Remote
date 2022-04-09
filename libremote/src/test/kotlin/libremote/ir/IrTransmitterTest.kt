package libremote.ir

import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * UT for the [IrTransmitter] class.
 *
 * @author Ofir Sela
 * @since 1.0.0
 */
class IrTransmitterTest {
    private object Constants {
        /** Dummy frequency to test. */
        const val CARRIER_FREQUENCY = 38222

        /** Dummy pulse burst length to test. */
        const val PULSE_BURST_LENGTH = 562.5
    }

    /**
     * The carrier frequency that was transmitted.
     */
    private var carrierFrequency: Int = 0

    /**
     * The pattern that was transmitted.
     */
    private var pattern: Iterable<Int> = arrayListOf()

    /**
     * Implementation of the transmit function, which simply sets the values of properties.
     */
    private val transmit: IrTransmitFunction = { carrierFrequency: Int, pattern: Iterable<Int> ->
        this.carrierFrequency = carrierFrequency
        this.pattern = pattern
    }

    /**
     * Instance of the transmitter.
     */
    private val irTransmitter =
        IrTransmitter(Constants.CARRIER_FREQUENCY, Constants.PULSE_BURST_LENGTH, transmit)

    /**
     * Tests that [IrTransmitter.transmit] calls the transmit function properly.
     */
    @Test
    fun testTransmitSanity() {
        irTransmitter.transmit(arrayListOf(1, 2, 3))
        assertEquals(Constants.CARRIER_FREQUENCY, carrierFrequency)
        assertEquals(arrayListOf(562, 1125, 1687), pattern)
    }
}