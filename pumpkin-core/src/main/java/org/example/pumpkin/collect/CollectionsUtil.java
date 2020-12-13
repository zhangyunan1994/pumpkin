
package org.example.pumpkin.collect;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.example.pumpkin.Preconditions;


public final class CollectionsUtil {

  private CollectionsUtil() {
  }

  public static boolean isNotEmpty(Collection coll) {
    return !isEmpty(coll);
  }

  public static boolean isNotEmpty(Map map) {
    return !isEmpty(map);
  }

  public static boolean isEmpty(Collection coll) {
    return coll == null || coll.isEmpty();
  }

  public static boolean isEmpty(Map map) {
    return map == null || map.isEmpty();
  }

  public static <T> T getFirstElseNull(List<T> params) {
    if (isEmpty(params)) {
      return null;
    }
    return params.get(0);
  }

  public static <T> T getLastElseNull(List<T> params) {
    if (isEmpty(params)) {
      return null;
    }
    return params.get(params.size() - 1);
  }

  static boolean safeContains(Collection<?> collection, Object object) {
    Preconditions.checkNotNull(collection);
    try {
      return collection.contains(object);
    } catch (ClassCastException | NullPointerException e) {
      return false;
    }
  }

  static boolean safeRemove(Collection<?> collection, Object object) {
    Preconditions.checkNotNull(collection);
    try {
      return collection.remove(object);
    } catch (ClassCastException | NullPointerException e) {
      return false;
    }
  }

  static boolean containsAllImpl(Collection<?> self, Collection<?> c) {
    for (Object o : c) {
      if (!self.contains(o)) {
        return false;
      }
    }
    return true;
  }

  static <T> Collection<T> cast(Iterable<T> iterable) {
    return (Collection<T>) iterable;
  }

}
