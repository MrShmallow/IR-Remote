package libremote.core.exception

import java.lang.Exception

/**
 * A generic exception in libremote.
 *
 * @author Ofir Sela
 * @since 1.0.0
 */
abstract class RemoteException : Exception {
    constructor() : super()
    constructor(message: String) : super(message)
    constructor(message: String, cause: Throwable) : super(message, cause)
    constructor(cause: Throwable) : super(cause)
}