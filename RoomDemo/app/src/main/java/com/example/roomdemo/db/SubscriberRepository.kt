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

    suspend fun insertSubscriber(subscriber: Subscriber) {
        dao.insertSubscriber(subscriber)
    }

    suspend fun updateSubscriber(subscriber: Subscriber) {
        dao.updateSubscriber(subscriber)
    }

    suspend fun deleteSubscriber(subscriber: Subscriber): Int {
        return dao.deleteSubscriber(subscriber)
    }

    suspend fun deleteAll() {
        dao.deleteAll()
    }
}
