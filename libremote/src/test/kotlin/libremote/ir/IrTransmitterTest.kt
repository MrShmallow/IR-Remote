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
        const val CARRIER_FREQUENCY = 1234

        /** Dummy pulse burst length to test. */
        const val PULSE_BURST_LENGTH = 1.5
    }

    /**
     * The carrier frequency that was transmitted.
     */
    private var carrierFrequency: Int = 0

    /**
     * The pattern that was transmitted.
     */
    private var pattern: Iterable<Int> = listOf()

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
     * Tests that [IrTransmitter.transmit] causes the expected pattern to be transmitted.
     */
    @Test
    fun testTransmitSanity() {
        irTransmitter.transmit(listOf(1, 2, 3, 4, 5))
        assertEquals(Constants.CARRIER_FREQUENCY, carrierFrequency)
        assertEquals(listOf(1, 3, 4, 6, 7), pattern)
    }
}