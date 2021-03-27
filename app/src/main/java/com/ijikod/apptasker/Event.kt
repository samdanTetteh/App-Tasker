package com.ijikod.apptasker

import androidx.lifecycle.Observer


/**
 * Used as a wrapper for data that is exposed via a LiveData that represents an event.
 */
open class Event<out T>(private val content: T) {

    //    @Suppress("MemberVisibilityCanBePrivate")
    var hasBeenHandled = false
        private set //Allow external read but not write


    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    /**
     * Returns the content, even if its already been handled
     * **/
    fun peekContent(): T = content
}

/**
 * An [Observer] for [Event]s. simplifying the pattern of checking if the [Event]'s content has
 * already been handled.
 *
 * [onEventUnHandledContent] is *only* called if the [Event]'s contents has been handled.
 * **/
class EventObserver<T>(private val onEventUnHandledContent: (T) -> Unit) : Observer<Event<T>> {
    override fun onChanged(event: Event<T>?) {
        event?.getContentIfNotHandled()?.let {
            onEventUnHandledContent(it)
        }
    }

}