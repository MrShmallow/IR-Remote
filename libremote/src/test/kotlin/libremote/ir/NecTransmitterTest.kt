package libremote.ir

import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * UT for the [NecTransmitter] class.
 *
 * @author Ofir Sela
 * @since 1.0.0
 */
class NecTransmitterTest {
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
    private val necTransmitter = NecTransmitter(transmit)

    /**
     * Tests that [IrTransmitter.transmit] causes the expected pattern to be transmitted.
     */
    @Test
    fun testTransmitSanity() {
        necTransmitter.transmit(NecTransmitter.NecMessage(0, 0xAD.toByte()))
        assertEquals(38222, carrierFrequency)
        assertEquals(
            listOf(
                // Header
                9000, 4500,
                // Address (0)
                562, 562, 562, 562, 562, 562, 562, 562,
                562, 562, 562, 562, 562, 562, 562, 562,
                // Inverse of address (255)
                562, 1687, 562, 1687, 562, 1687, 562, 1687,
                562, 1687, 562, 1687, 562, 1687, 562, 1687,
                // Command (0xAD)
                562, 1687, 562, 562, 562, 1687, 562, 1687,
                562, 562, 562, 1687, 562, 562, 562, 1687,
                // Inverse of command (0x52)
                562, 562, 562, 1687, 562, 562, 562, 562,
                562, 1687, 562, 562, 562, 1687, 562, 562,
                // Trailer
                562
            ),
            pattern
        )
    }
}