package libremote.core

/**
 * A generic button.
 *
 * @author Ofir Sela
 * @since 1.0.0
 */
interface Button {
    /**
     * Name of the button.
     */
    val name: String

    /**
     * Clicks the button - performs the operation assigned to it.
     *
     * @param remote The remote which the button belongs to.
     */
    fun onClick(remote: Remote)
}