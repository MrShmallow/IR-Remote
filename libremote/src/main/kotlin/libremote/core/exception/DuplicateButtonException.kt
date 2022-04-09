package libremote.core.exception

class DuplicateButtonException(buttonName: String) :
    RemoteException("Button named \"$buttonName\" is already registered to the remote")