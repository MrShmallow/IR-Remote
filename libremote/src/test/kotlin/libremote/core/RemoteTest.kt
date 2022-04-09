package libremote.core

import libremote.core.exception.ButtonNotFoundException
import libremote.core.exception.DuplicateButtonException
import kotlin.test.*

/**
 * UT for the [Remote] class.
 *
 * @author Ofir Sela
 * @since 1.0.0
 */
class RemoteTest {
    /**
     * Button which sets the [clicked] flag to true when clicking it.
     */
    private val button = object: Button {
        override val name: String
            get() = "TestButton"

        override fun onClick(remote: Remote) {
            clicked = true
        }
    }

    /**
     * Instance of the remote.
     */
    private val remote = Remote()

    /**
     * Flag which indicates whether the button was clicked.
     */
    private var clicked = false

    init {
        remote.registerButton(button)
    }

    /**
     * Tests that [Remote.click] actually clicks the button.
     */
    @Test
    fun testClickSanity() {
        remote.click(button.name)
        assertTrue(clicked)
    }

    /**
     * Tests that trying to click a button with a name that doesn't exist in the remote throws an
     * exception.
     */
    @Test
    fun testClickMissingButton() {
        assertFailsWith<ButtonNotFoundException> {
            remote.click("NonExistent")
        }
        assertFalse(clicked)
    }

    /**
     * Tests that trying to click a button which was unregistered throws an exception.
     */
    @Test
    fun testClickUnregisteredButton() {
        remote.unregisterButton(button.name)
        assertFailsWith<ButtonNotFoundException> {
            remote.click(button.name)
        }
        assertFalse(clicked)
    }

    /**
     * Tests that trying to register the same button twice throws an exception.
     */
    @Test
    fun testRegisterDuplicateButton() {
        assertFailsWith<DuplicateButtonException> {
            remote.registerButton(button)
        }
    }

    /**
     * Tests that trying to unregister a button with a name that was never registered throws an
     * exception.
     */
    @Test
    fun testUnregisterMissingButton() {
        assertFailsWith<ButtonNotFoundException> {
            remote.unregisterButton("NonExistent")
        }
    }
}