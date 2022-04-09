package libremote.core

import libremote.core.exception.ButtonNotFoundException
import libremote.core.exception.DuplicateButtonException

class Remote {
    private val buttons: MutableMap<String, Button> = HashMap()

    fun registerButton(button: Button) {
        if (buttons.containsKey(button.name)) {
            throw DuplicateButtonException(button.name)
        }
        buttons[button.name] = button
    }

    fun unregisterButton(buttonName: String) {
        buttons.remove(buttonName) ?: throw ButtonNotFoundException(buttonName)
    }

    fun click(buttonName: String) {
        val button = getButtonByName(buttonName)
        button.onClick(this, button)
    }

    private fun getButtonByName(buttonName: String): Button {
        return buttons[buttonName] ?: throw ButtonNotFoundException(buttonName)
    }
}