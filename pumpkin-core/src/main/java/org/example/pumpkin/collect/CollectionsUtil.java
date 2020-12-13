
package org.example.pumpkin.collect;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.example.pumpkin.Preconditions;

/**
 * 集合通用处理
 */
public final class CollectionsUtil {

  private CollectionsUtil() {
  }

  /**
   * Is not empty boolean.
   * <p>
   * 判断是不是一个空集合
   *
   * @param coll the coll
   * @return the boolean
   */
  public static boolean isNotEmpty(Collection coll) {
    return !isEmpty(coll);
  }

  /**
   * Is not empty boolean.
   * <p>
   * 判断是不是一个空map
   *
   * @param map the map
   * @return the boolean
   */
  public static boolean isNotEmpty(Map map) {
    return !isEmpty(map);
  }

  /**
   * Is empty boolean.
   *
   * @param coll the coll
   * @return the boolean
   */
  public static boolean isEmpty(Collection coll) {
    return coll == null || coll.isEmpty();
  }

  /**
   * Is empty boolean.
   *
   * @param map the map
   * @return the boolean
   */
  public static boolean isEmpty(Map map) {
    return map == null || map.isEmpty();
  }

  /**
   * Gets first else null.
   * <p>
   * 获取List集合的第一个元素，如果没有则返回空
   *
   * @param <T>    the type parameter
   * @param params the params
   * @return the first else null
   */
  public static <T> T getFirstElseNull(List<T> params) {
    if (isEmpty(params)) {
      return null;
    }
    return params.get(0);
  }

  /**
   * Gets last else null.
   * <p>
   * 获取 list 的最后一个元素
   *
   * @param <T>    the type parameter
   * @param params the params
   * @return the last else null
   */
  public static <T> T getLastElseNull(List<T> params) {
    if (isEmpty(params)) {
      return null;
    }
    return params.get(params.size() - 1);
  }

  /**
   * Safe contains boolean.
   * <p>
   * 判断是否包含关系
   *
   * @param collection the collection
   * @param object     the object
   * @return the boolean
   */
  static boolean safeContains(Collection<?> collection, Object object) {
    Preconditions.checkNotNull(collection);
    try {
      return collection.contains(object);
    } catch (ClassCastException | NullPointerException e) {
      return false;
    }
  }

  /**
   * Safe remove boolean.
   * <p>
   * 删除制定的项目
   *
   * @param collection the collection
   * @param object     the object
   * @return the boolean
   */
  static boolean safeRemove(Collection<?> collection, Object object) {
    Preconditions.checkNotNull(collection);
    try {
      return collection.remove(object);
    } catch (ClassCastException | NullPointerException e) {
      return false;
    }
  }

  /**
   * Contains all boolean.
   *
   * @param self the self
   * @param c    the c
   * @return the boolean
   */
  static boolean containsAllImpl(Collection<?> self, Collection<?> c) {
    for (Object o : c) {
      if (!self.contains(o)) {
        return false;
      }
    }
    return true;
  }

  /**
   * Cast collection.
   *
   * @param <T>      the type parameter
   * @param iterable the iterable
   * @return the collection
   */
  static <T> Collection<T> cast(Iterable<T> iterable) {
    return (Collection<T>) iterable;
  }

}
