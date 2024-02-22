package com.example.roomdemo.db

class SubscriberRepository(
    private val dao: SubscriberDAO
) {
    /*
    We don't need any threading mechanism here as QUERY database operation uses Coroutines internally
     */
    /***
     * subscribers = dao.getAllSubscribers() -->
     * We don't need any threading mechanism here as QUERY database operation uses Coroutines internally
     */
    val subscribers = dao.getAllSubscribers()

    suspend fun insertSubscriber(subscriber: Subscriber): Long {
        return dao.insertSubscriber(subscriber)
    }

    suspend fun updateSubscriber(subscriber: Subscriber): Int {
        return dao.updateSubscriber(subscriber)
    }

    suspend fun deleteSubscriber(subscriber: Subscriber): Int {
        return dao.deleteSubscriber(subscriber)
    }

    suspend fun deleteAll(): Int {
        return dao.deleteAll()
    }
}
