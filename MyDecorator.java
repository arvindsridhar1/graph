package graph;

import java.util.HashMap;
import java.util.Set;

import support.graph.CS16Decorator;

/**
 * 
 * Your implementation of decorations. All methods must run in O(1) time.
 * 
 * <p>
 * This program makes heavy use of the decorator pattern. It is
 * absolutely essential that you understand this design pattern before you start
 * coding. In previous programs, like Heap, you were allowed to modify your
 * Position classes when you wanted that class to store an additional piece of
 * information. However, when you want to potentially store a lot of things in
 * your classes, and especially when you do not know in advance what you will be
 * storing, the decorator's usefulness is profound.
 * </p>
 *
 * <p>
 * Think of the decorator pattern like a post-it
 * note. You write what you want on it, then stick it on the class. Later on,
 * when you want to retrieve that information, you just retrieve the note, and
 * read what it has to say. Please refer to the handout for more details on the
 * decorator pattern and some examples on its usage.
 * </p>
 *
 * 
 */
public class MyDecorator<K, V> implements CS16Decorator<K, V> {

  public MyDecorator() {

  }

  /**
   * Gets the decoration associated with the given key.
   *
   * @param key
   *          the key used to retrieve a specific value.
   * @return the value associated with the key parameter
   */
  @Override
  public V getDecoration(K key) {
      return null;
  }

  /**
   * Sets the decoration for the specified key to value.
   *
   * @param key
   *          the key that will be used to retrieve your value
   * @param value
   *          the value associated with your key
   */
  @Override
  public void setDecoration(K key, V value) {

  }

  /**
   * Returns true if there is a decoration for the given key, false otherwise.
   *
   * @param key
   *          a key that may or may not be in your decorator
   * @return a boolean value that returns true if the key is valid
   */
  @Override
  public boolean hasDecoration(K key) {
    return false;
  }

  /**
   * Removes the decoration for the given key and returns the value associated
   * with it.
   *
   * @param key
   *          key associated with a value
   * @return value removed
   */
  @Override
  public V removeDecoration(K key) {
    return null;
  }

  /**
   * Returns a Set of all keys for this decoration.
   *
   * @return a Set of all keys in your decorator
   */
  @Override
  public Set<K> getKeys() {
    return null;
  }
}
