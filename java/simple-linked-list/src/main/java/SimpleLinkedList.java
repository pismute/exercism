import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public class SimpleLinkedList<A> {
    private List<A> list = List.empty();

    public SimpleLinkedList() {
        list = List.empty();
    }

    public SimpleLinkedList(A[] list) {
        this.list = List.of(list);
    }

    public int size() {
        return list.size();
    }

    public A pop() {
        A a = list.getHead();
        list = list.getTail();
        return a;
    }

    public void push(A a) {
        list = list.add(a);
    }

    public void reverse() {
        list = list.reverse();
    }

    public A[] asArray(Class<A> cls) {
        return list.toArray();
    }

    public void foreach(Consumer<A> f) {
        list.foreach(f);
    }

    public interface List<A> {
        A getHead();
        List<A> getTail();
        List<A> add(A a);

        default int size() {
            int len = 0;
            List<A> list = this;
            while(!list.isEmpty()) {
                len++;
                list = list.getTail();
            }

            return len;
        }

        default boolean isEmpty() {
            return false;
        }

        default List<A> reverse() {
            return foldLeft(empty(), (acc, a) -> acc.add(a));
        }

        default <Z> Z foldLeft(Z z, BiFunction<Z, ? super A, Z> f) {
            List<A> list = this;
            Z zz = z;
            while(!list.isEmpty()) {
                zz = f.apply(zz, list.getHead());
                list = list.getTail();
            }

            return zz;
        }

        default void foreach(Consumer<A> f) {
            List<A> list = this;
            while(!list.isEmpty()) {
                f.accept(list.getHead());
                list = list.getTail();
            }
        }

        default A[] toArray() {
            return (A[])this.<ArrayList<A>>foldLeft(new ArrayList<A>(), (acc, a)-> {
                acc.add(a);
                return acc;
            }).toArray();

        }

        static <B> List<B> empty() {
            return (List<B>) EMPTY;
        }

        static <B> List<B> of(B[] as) {
            requireNonEmpty(as);

            List<B> list = List.empty();
            for(int i = as.length -1; i >= 0; i -= 1) {
                list = list.add(as[i]);
            }

            return list;
        }
    }

    public static class ListImpl<A> implements List<A>{
        private final A head;
        private final ListImpl<A> tail;

        protected ListImpl() {
            this(null, null);
        }

        public ListImpl(A head, ListImpl<A> tail) {
            this.head = head;
            this.tail = tail;
        }

        @Override
        public A getHead() {
            Objects.requireNonNull(head);

            return head;
        }

        @Override
        public List<A> getTail() {
            Objects.requireNonNull(tail);

            return tail;
        }

        @Override
        public List<A> add(A a) {
            return new ListImpl<A>(a, this);
        }
    }

    private static final List<?> EMPTY = new ListImpl<Object>() {
        @Override
        public Object getHead() {
            throw new NoSuchElementException("empty");
        }

        @Override
        public List<Object> getTail() {
            throw new NoSuchElementException("empty");
        }

        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return true;
        }
    };

    public static <B> void requireNonEmpty(B[] as) {
        if(as == null || as.length == 0) throw new IllegalArgumentException(as + " is empty");
    }
}

