package by.clevertec.cache.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class LfuCacheTest {
    @Test
    public void testLFUCachePutAndGet() {
        // Given
        LfuCache<Integer, String> cache = new LfuCache<>(3);
        cache.put(1, "one");
        cache.put(2, "two");
        cache.put(3, "three");

        // When
        String result = cache.get(1);

        // Then
        assertEquals("one", result);
    }

    @Test
    public void testLFUCacheEviction() {
        // Given
        LfuCache<Integer, String> cache = new LfuCache<>(3);
        cache.put(1, "one");
        cache.put(2, "two");
        cache.put(3, "three");

        // When
        cache.put(4, "four");
        String result = cache.get(1);

        // Then
        assertNull(result);
    }

    @Test
    public void testLFUCacheRemove() {
        // Given
        LfuCache<Integer, String> cache = new LfuCache<>(3);
        cache.put(1, "one");
        cache.put(2, "two");
        cache.put(3, "three");

        // When
        cache.remove(2);
        String result = cache.get(2);

        // Then
        assertNull(result);
    }
}