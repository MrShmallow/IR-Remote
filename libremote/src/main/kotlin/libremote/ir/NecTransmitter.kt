package libremote.ir

import libremote.core.Transmitter
import kotlin.experimental.inv

/**
 * Transmitter for IR data following the NEC protocol.
 *
 * For more information, see
 * [NEC Infrared Transmission Protocol](https://techdocs.altium.com/display/FPGA/NEC+Infrared+Transmission+Protocol).
 *
 * @param transmit The actual implementation for the transmission function.
 *
 * @author Ofir Sela
 * @since 1.0.0
 */
class NecTransmitter(transmit: IrTransmitFunction) :
    Transmitter<NecTransmitter.NecMessage> {
    private object Constants {
        /**
         * IR frequency for NEC transmissions, in Hertz.
         */
        const val CARRIER_FREQUENCY = 38222

        /**
         * Length of a single pulse burst for NEC transmissions, in microseconds.
         */
        const val PULSE_BURST_LENGTH = 562.5

        /**
         * Pulses pattern sent at the beginning of NEC transmissions.
         */
        val HEADER_PULSES: Iterable<Int> = listOf(16, 8)

        /**
         * Pulses pattern sent at the end of NEC transmissions.
         */
        val TRAILER_PULSES: Iterable<Int> = listOf(1)

        /**
         * Pulses pattern which represents a 0 bit in NEC transmissions.
         */
        val ZERO_PULSES: Iterable<Int> = listOf(1, 1)

        /**
         * Pulses pattern which represents a 1 bit in NEC transmissions.
         */
        val ONE_PULSES: Iterable<Int> = listOf(1, 3)

        /**
         * Pulses pattern which represent a repeat code.
         */
        val REPEAT_CODE_PULSES = listOf(16, 4, 1)
    }

    /**
     * The data for NEC protocol messages.
     *
     * @property address Address of the receiving device.
     * @property command Command to perform on the device.
     */
    data class NecMessage(
        val address: Byte,
        val command: Byte
    )

    /**
     * Internal IR transmitter to transmit messages with.
     */
    private val irTransmitter =
        IrTransmitter(Constants.CARRIER_FREQUENCY, Constants.PULSE_BURST_LENGTH, transmit)

    /**
     * Transmits the given NEC message.
     *
     * The total length of the transmission is 67.5 milliseconds.
     *
     * @param data The message to transmit.
     *
     * @see Transmitter.transmit
     */
    override fun transmit(data: NecMessage) {
        /*
         * |------------------------------------------------------------|
         * | header | address | ~address | command | ~command | trailer |
         * |------------------------------------------------------------|
         */
        val irData = Constants.HEADER_PULSES +
                byteToPulses(data.address) +
                byteToPulses(data.address.inv()) +
                byteToPulses(data.command) +
                byteToPulses(data.command.inv()) +
                Constants.TRAILER_PULSES
        irTransmitter.transmit(irData)
    }

    /**
     * Transmits a repeat code.
     *
     * This code should be sent while the key is held down on the transmitting remote controller,
     * typically 40 milliseconds after the last transmission, and in intervals of 108 milliseconds.
     */
    fun transmitRepeat() {
        irTransmitter.transmit(Constants.REPEAT_CODE_PULSES)
    }

    /**
     * Converts the given byte into a list of pulses, in little-endian.
     *
     * @param byte The byte to convert.
     *
     * @return List of pulses representing the byte.
     */
    private fun byteToPulses(byte: Byte): Iterable<Int> {
        val pulses = mutableListOf<Int>()
        // For each bit, add the pattern matching its value.
        for (i in 0 until Byte.SIZE_BITS) {
            pulses += if (isBitOn(byte, i)) Constants.ONE_PULSES else Constants.ZERO_PULSES
        }
        return pulses.toList()
    }

    /**
     * Returns whether the bit in the specified position is set in the byte.
     *
     * @param byte The byte
     * @param position The position in the byte, with the LSB as 0 and the MSB as 7.
     *
     * @return true if the bit is set, false if not.
     */
    private fun isBitOn(byte: Byte, position: Int): Boolean {
        // (byte >> position) & 1
        return ((byte.toInt() shr position) and 1) == 1
    }
}