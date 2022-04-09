package libremote.core.exception

/**
 * Exception thrown when a button is not found in a remote.
 *
 * @param buttonName Name of the button.
 *
 * @author Ofir Sela
 * @since 1.0.0
 */
class ButtonNotFoundException(buttonName: String) :
    RemoteException("Button named \"$buttonName\" was not found in the remote")