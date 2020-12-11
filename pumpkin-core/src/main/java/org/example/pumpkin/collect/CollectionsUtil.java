
package org.example.pumpkin.collect;

import java.util.Collection;
import org.example.pumpkin.Preconditions;


public final class CollectionsUtil {

  private CollectionsUtil() {
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
