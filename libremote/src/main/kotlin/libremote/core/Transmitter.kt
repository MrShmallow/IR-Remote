package libremote.core

/**
 * A generic interface for objects that can transmit data.
 *
 * @author Ofir Sela
 * @since 1.0.0
 */
interface Transmitter {
    /**
     * Transmits the given data.
     *
     * @param data The data to transmit, as a list of integers.
     */
    fun transmit(data: Iterable<Int>)
}