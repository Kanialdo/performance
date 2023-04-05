package pl.krystiankaniowski.performance.domain.settings

object SettingsOrder {

    internal var autoOrder: Int = 0
        get() {
            field += 1
            return field
        }

    val DND_HEADER = autoOrder
    val DND_ENABLED = autoOrder
    val DND_ANDROID_SETTINGS = autoOrder
    val NOTIFICATIONS_HEADER = autoOrder
    val NOTIFICATION_ANDROID_APP_SETTINGS = autoOrder
    val NOTIFICATION_SHOW_TIME_ENABLED = autoOrder
    val AWAKE_KEEP_AWAKE = autoOrder
    val STATS_DEV_GENERATE_HISTORY = autoOrder
    val STATS_REMOVE_HISTORY = autoOrder
    val APP_VERSION = autoOrder

    data class Entry(val category: Category, val order: Int)

    sealed interface Pos {
        object DND : Pos {
            private val order = AutoOrder()
            val DND_ENABLED = order.next()
            val DND_ANDROID_SETTINGS = order.next()
        }
    }

    enum class Category {
        DND,
        NOTIFICATIONS,
        STATS,
        OTHERS,
        ABOUT,
    }

    enum class Order(category: Category) {

    }

//    sealed interface Category {
//        val order: Int
//
//        object Dnd : Category {
//            override val order: Int = autoOrder
//            val isEnabled = autoOrder
//            val androidSettings = autoOrder
//        }
//        object Notifications : Category {
//            override val order: Int = autoOrder
//            val androidAppSettings = autoOrder
//            val isShowTimeEnabled = autoOrder
//        }
//    }
}

data class AutoOrder(private var order: Int = 0) {
    fun next(): Int {
        order++
        return order
    }
}