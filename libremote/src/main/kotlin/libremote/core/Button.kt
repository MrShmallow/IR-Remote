package libremote.core

interface Button {
    val name: String
    fun onClick(remote: Remote, button: Button)
}