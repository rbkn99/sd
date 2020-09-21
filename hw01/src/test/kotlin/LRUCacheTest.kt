import org.junit.Assert
import org.junit.Test
import kotlin.random.Random

class LRUCacheTest {
    @Test fun emptyTest() {
        val cache = LRUCache<Int, Int>(1)
        Assert.assertNull(cache.get(0))
    }

    @Test fun getTest() {
        val cache = LRUCache<Int, Int>(3)
        cache.put(0, 10)
        cache.put(1, 11)
        cache.put(2, 12)
        Assert.assertEquals(11, cache.get(1))
        Assert.assertNull(cache.get(3))
    }

    @Test fun cacheMissPutTest() {
        val cache = LRUCache<Int, Int>(3)
        cache.put(0, 10)
        cache.put(1, 11)
        cache.put(2, 12)
        cache.put(3, 13)
        Assert.assertNull(cache.get(0))
        Assert.assertEquals(11, cache.get(1))
    }

    @Test fun cacheMissGetTest() {
        val cache = LRUCache<Int, Int>(3)
        cache.put(0, 10)
        cache.put(1, 11)
        cache.put(2, 12)
        Assert.assertEquals(10, cache.get(0))
        cache.put(3, 13)
        Assert.assertNull(cache.get(1))
    }

    @Test fun stressTest() {
        val cache = LRUCache<Int, Int>(20)
        val rand = Random(42)
        val maxKey = 30
        repeat(10000000) {
            if (rand.nextBoolean()) {
                cache.get(rand.nextInt() % maxKey)
            } else {
                cache.put(rand.nextInt() % maxKey, rand.nextInt())
            }
        }
    }
}
