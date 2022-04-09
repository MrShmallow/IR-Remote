package libremote.core.exception

/**
 * Exception thrown when trying to register a button with the same name as an already-registered
 * button.
 *
 * @param buttonName Name of the button.
 *
 * @author Ofir Sela
 * @since 1.0.0
 */
class DuplicateButtonException(buttonName: String) :
    RemoteException("Button named \"$buttonName\" is already registered to the remote")