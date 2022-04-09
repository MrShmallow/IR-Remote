package libremote.core

/**
 * A generic interface for objects that can transmit data.
 *
 * @param T The type of data that will be transmitted.
 *
 * @author Ofir Sela
 * @since 1.0.0
 */
interface Transmitter<T> {
    /**
     * Transmits the given data.
     *
     * @param data The data to transmit.
     */
    fun transmit(data: T)
}