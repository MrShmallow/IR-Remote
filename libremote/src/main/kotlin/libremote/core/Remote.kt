package libremote.core

import libremote.core.exception.ButtonNotFoundException
import libremote.core.exception.DuplicateButtonException

/**
 * A generic remote which allows registering, unregistering and clicking buttons.
 *
 * @author Ofir Sela
 * @since 1.0.0
 */
class Remote {
    /**
     * Map between button names and button objects.
     */
    private val buttons: MutableMap<String, Button> = HashMap()

    /**
     * Adds a new button to the remote.
     *
     * @param button The button to register.
     *
     * @throws DuplicateButtonException if a button with the same name was already registered.
     */
    fun registerButton(button: Button) {
        if (buttons.containsKey(button.name)) {
            throw DuplicateButtonException(button.name)
        }
        buttons[button.name] = button
    }

    /**
     * Removes a button from the remote.
     *
     * @param buttonName Name of the button to unregister.
     *
     * @throws ButtonNotFoundException if the button name doesn't match any registered button.
     */
    fun unregisterButton(buttonName: String) {
        buttons.remove(buttonName) ?: throw ButtonNotFoundException(buttonName)
    }

    /**
     * Clicks a button - calls the [Button.onClick] method of the button with the specified name.
     *
     * @param buttonName Name of the button to click.
     *
     * @throws ButtonNotFoundException if a button with the specified name was not found.
     */
    fun click(buttonName: String) {
        getButtonByName(buttonName).onClick(this)
    }

    /**
     * Returns a registered button that matches the specified name.
     *
     * @param buttonName Name of the button to search for.
     *
     * @throws ButtonNotFoundException if a button with the specified name was not found.
     */
    private fun getButtonByName(buttonName: String): Button {
        return buttons[buttonName] ?: throw ButtonNotFoundException(buttonName)
    }
}