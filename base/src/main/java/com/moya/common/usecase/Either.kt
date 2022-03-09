package com.moya.common.usecase

import com.moya.common.usecase.Either.Left
import com.moya.common.usecase.Either.Right
import retrofit2.Call

/**
 * Represents a value of one of two possible types (a disjoint union).
 * Instances of [Either] are either an instance of [Left] or [Right].
 * FP Convention dictates that [Left] is used for "failure"
 * and [Right] is used for "success".
 *
 * @see Left
 * @see Right
 */
sealed class Either<out L, out R> {
    /** * Represents the left side of [Either] class which by convention is a "Failure". */
    data class Left<out L>(val a: L) : Either<L, Nothing>()

    /** * Represents the right side of [Either] class which by convention is a "Success". */
    data class Right<out R>(val b: R) : Either<Nothing, R>()

    /**
     * Returns true if this is a Right, false otherwise.
     * @see Right
     */
    val isRight get() = this is Right<R>

    /**
     * Returns true if this is a Left, false otherwise.
     * @see Left
     */
    val isLeft get() = this is Left<L>

    /**
     * Creates a Left type.
     * @see Left
     */
    fun <L> left(a: L) = Left(a)


    /**
     * Creates a Left type.
     * @see Right
     */
    fun <R> right(b: R) = Right(b)

    /**
     * Applies fnL if this is a Left or fnR if this is a Right.
     * @see Left
     * @see Right
     */
    fun fold(fnL: (L) -> Any, fnR: (R) -> Any): Any =
        when (this) {
            is Left -> fnL(a)
            is Right -> fnR(b)
        }
}

/**
 * Composes 2 functions
 * See <a href="https://proandroiddev.com/kotlins-nothing-type-946de7d464fb">Credits to Alex Hart.</a>
 */
fun <A, B, C> ((A) -> B).c(f: (B) -> C): (A) -> C = {
    f(this(it))
}

/**
 * Right-biased flatMap() FP convention which means that Right is assumed to be the default case
 * to operate on. If it is Left, operations like map, flatMap, ... return the Left value unchanged.
 */
fun <T, L, R> Either<L, R>.flatMap(fn: (R) -> Either<L, T>): Either<L, T> =
    when (this) {
        is Left -> Left(a)
        is Right -> fn(b)
    }

/**
 * Right-biased map() FP convention which means that Right is assumed to be the default case
 * to operate on. If it is Left, operations like map, flatMap, ... return the Left value unchanged.
 */
fun <T, L, R> Either<L, R>.map(fn: (R) -> (T)): Either<L, T> = this.flatMap(fn.c(::right))

/** Returns the value from this `Right` or the given argument if this is a `Left`.
 *  Right(12).getRightOrElse(17) RETURNS 12 and Left(12).getRightOrElse(17) RETURNS 17
 */
fun <L, R> Either<L, R>.getRightOrElse(value: R): R =
    when (this) {
        is Left -> value
        is Right -> b
    }

/** Returns the value from this `Left` or the given argument if this is a `Right`.
 *  Left(12).getLeftOrElse(17) RETURNS 12 and Right(12).getLeftOrElse(17) RETURNS 17
 */
fun <L, R> Either<L, R>.getLeftOrElse(value: L): L =
    when (this) {
        is Left -> a
        is Right -> value
    }

/**
 * Left-biased onFailure() FP convention dictates that when this class is Left, it'll perform
 * the onFailure functionality passed as a parameter, but, overall will still return an either
 * object so you chain calls.
 */
fun <L, R> Either<L, R>.onFailure(fn: (failure: L) -> Unit): Either<L, R> =
    this.apply { if (this is Left) fn(a) }

/**
 * Right-biased onSuccess() FP convention dictates that when this class is Right, it'll perform
 * the onSuccess functionality passed as a parameter, but, overall will still return an either
 * object so you chain calls.
 */
fun <L, R> Either<L, R>.onSuccess(fn: (success: R) -> Unit): Either<L, R> =
    this.apply { if (this is Right) fn(b) }

fun <T, L, R> Either<L, R>.transfer(fn: (success: R) -> Either<L, T>) =
    when (this) {
        is Left -> this
        is Right -> fn(b)
    }

/**
 * Execute a request call api and return an Either object with specific Failures or successful response
 *
 * @param call an invocation of a Retrofit method that sends a request to a webserver and returns a response
 * @param transform transform result of response to other object
 * @param default default value if response is successful but body is null
 * */
fun <T, R> requestApi(
    call: Call<T>,
    transform: (T) -> R,
    default: T
): Either<Failure, R> {
    return try {
        val response = call.execute()
        when (response.code()) {
            in 200 until 300 -> Right(transform((response.body() ?: default)))
            in 300 until 500 -> Left(Failure.ClientError(message = response.message()))
            else -> Left(Failure.ServerError(message = response.message()))
        }
    } catch (exception: Exception) {
        when (exception) {
            is NetworkUnavailableException -> Left(Failure.NetworkConnection)
            else -> Left(Failure.ClientError(exception = exception))
        }
    }
}