import java.util.Optional;

public class Deque<A> {
    private Optional<Node<A>> head;
    private Optional<Node<A>> tail;

    public Deque() {
        this.head = Optional.empty();
        this.tail = Optional.empty();
    }

    public void push(A a) {
        Optional<Node<A>> optionalNode = Node.optionalOf(a, Optional.empty(), tail);

        tail.ifPresent(node-> node.setNext(optionalNode));

        tail = optionalNode;

        if(!head.isPresent()) head = tail;
    }

    public A pop() {
        Node<A> detached = tail.get();

        tail = tail.flatMap(node-> node.getPrev());

        if(!tail.isPresent()) head = tail;

        return detached.getValue();
    }

    public A shift() {
        Node<A> detached = head.get();

        head = head.flatMap(node-> node.getNext());

        if(!head.isPresent()) tail = head;

        return detached.getValue();
    }

    public void unshift(A a) {
        Optional<Node<A>> optionalNode = Node.optionalOf(a, head, Optional.empty());

        head.ifPresent(node-> node.setPrev(optionalNode));

        head = optionalNode;

        if(!tail.isPresent()) tail = head;
    }

    public static class Node<A> {
        private final A value;
        private Optional<Node<A>> next;
        private Optional<Node<A>> prev;

        public Node(A value, Optional<Node<A>> next, Optional<Node<A>> prev) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }

        public A getValue() {
            return value;
        }

        public Optional<Node<A>> getNext() {
            return next;
        }

        public Optional<Node<A>> getPrev() {
            return prev;
        }

        public void setNext(Optional<Node<A>> next) {
            this.next = next;
        }

        public void setPrev(Optional<Node<A>> prev) {
            this.prev = prev;
        }

        public static <B> Optional<Node<B>> optionalOf(B a, Optional<Node<B>> next, Optional<Node<B>> prev) {
            return Optional.of(new Node(a, next, prev));
        }
    }
}
