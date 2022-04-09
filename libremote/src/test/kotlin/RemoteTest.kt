import libremote.core.Remote
import libremote.core.Button
import libremote.core.exception.ButtonNotFoundException
import libremote.core.exception.DuplicateButtonException
import kotlin.test.*

class RemoteTest {
    private val button = object: Button {
        override val name: String
            get() = "TestButton"

        override fun onClick(remote: Remote, button: Button) {
            clicked = true
        }
    }
    private val remote = Remote()
    private var clicked = false

    init {
        remote.registerButton(button)
    }

    @Test
    fun testClickSanity() {
        remote.click(button.name)
        assertTrue(clicked)
    }

    @Test
    fun testClickMissingButton() {
        assertFailsWith<ButtonNotFoundException> {
            remote.click("NonExistent")
        }
        assertFalse(clicked)
    }

    @Test
    fun testClickUnregisteredButton() {
        remote.unregisterButton(button.name)
        assertFailsWith<ButtonNotFoundException> {
            remote.click(button.name)
        }
        assertFalse(clicked)
    }

    @Test
    fun testRegisterDuplicateButton() {
        assertFailsWith<DuplicateButtonException> {
            remote.registerButton(button)
        }
    }

    @Test
    fun testUnregisterMissingButton() {
        assertFailsWith<ButtonNotFoundException> {
            remote.unregisterButton("NonExistent")
        }
    }
}