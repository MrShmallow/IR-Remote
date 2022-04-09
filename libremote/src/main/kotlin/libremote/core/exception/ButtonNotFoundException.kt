package libremote.core.exception

class ButtonNotFoundException(buttonName: String) :
    RemoteException("Button named \"$buttonName\" was not found in the remote")