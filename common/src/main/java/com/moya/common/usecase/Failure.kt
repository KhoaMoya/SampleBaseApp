package com.moya.common.usecase

import com.moya.common.usecase.Failure.FeatureFailure

/**
 * Base Class for handling errors/failures/exceptions.
 * Every feature specific failure should extend [FeatureFailure] class.
 */
sealed class Failure {
    object NetworkConnection : Failure()
    class ClientError(val message: String? = null, val exception: Exception? = null) : Failure()
    class ServerError(val message: String? = null) : Failure()

    /** * Extend this class for feature specific failures.*/
    abstract class FeatureFailure : Failure()
}

class NetworkUnavailableException : Exception()