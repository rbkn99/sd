class LRUCache<K, V>(size: Int) {
    init {
        assert(size > 0) { "size must be > 0" }
    }
    private val maxSize: Int = size
    private val hashMap: MutableMap<K, Node<K, V>> = mutableMapOf()
    private var head: Node<K, V>? = null
    private var tail: Node<K, V>? = null

    fun put(key: K, value: V) {
        doPut(key, value)
        assert(head != null && tail != null) { "linked list is empty or invalid" }
        assert(head?.value == value) { "top of the list must be the inserted value" }
        assert(hashMap.containsKey(key)) { "hash map doesn't contains inserted value" }
        assert(size() <= maxSize) { "cache is overflowed" }
    }

    fun get(key: K): V? {
        val oldSize = size()
        val value = doGet(key)
        assert(value == null || head?.value == value) { "top of the list must be the requested value" }
        assert(oldSize == size()) { "size is changed" }
        return value
    }

    fun size(): Int {
        return hashMap.size
    }

    private fun doPut(key: K, value: V) {
        remove(key)
        addNode(key, value)
    }

    private fun doGet(key: K): V? {
        if (!hashMap.containsKey(key)) {
            return null
        }
        val value = hashMap[key]!!.value
        remove(key)
        addNode(key, value)
        return value
    }

    private fun addNode(key: K, value: V) {
        val node = Node(key, value, null, head)
        head?.prev = node
        head = node
        if (tail == null) {
            tail = head
        }
        hashMap[key] = node
        if (size() > maxSize) {
            remove(tail!!.key)
        }
    }

    private fun remove(key: K) {
        if (!hashMap.containsKey(key)) {
            return
        }
        val node = hashMap[key]!!
        node.next?.prev = node.prev
        node.prev?.next = node.next
        if (node.next == null) {
            tail = node.prev
        }
        if (node.prev == null) {
            head = node.next
        }
        hashMap.remove(key)
    }

    data class Node<K, V>(val key: K,
                          var value: V,
                          var prev: Node<K, V>?,
                          var next: Node<K, V>?)
}