import java.util.NoSuchElementException;

public class Deque<A> {
    private final HeadNode<A> head;
    private final TailNode<A> tail;

    public Deque() {
        this.head = new HeadNode<>(null);
        this.tail = new TailNode<>(this.head);
        this.head.setNext(this.tail);
    }

    public void push(A a) {
        tail.push(a);
    }

    public A pop() {
        return tail.pop();
    }

    public A shift() {
        return head.shift();
    }

    public void unshift(A a) {
        head.unshift(a);
    }

    public static class Node<A> {
        private final A value;
        private Node<A> next;
        private Node<A> prev;

        public Node(A value, Node<A> next, Node<A> prev) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }

        public A getValue() {
            return value;
        }

        public Node<A> getNext() {
            return next;
        }

        public Node<A> getPrev() {
            return prev;
        }

        public void setNext(Node<A> next) {
            this.next = next;
        }

        public void setPrev(Node<A> prev) {
            this.prev = prev;
        }

        public boolean isEmpty() {
            return false;
        }
    }

    public static class HeadNode<A> extends Node<A> {
        public HeadNode(Node<A> next) {
            super(null, next, null);
        }

        @Override
        public A getValue() {
            throw new NoSuchElementException("head empty");
        }

        @Override
        public Node<A> getPrev() {
            throw new NoSuchElementException("head empty");
        }

        @Override
        public void setPrev(Node<A> prev) {
            throw new NoSuchElementException("head empty");
        }

        @Override
        public boolean isEmpty() {
            return true;
        }

        public void unshift(A a) {
            Node<A> node = new Node<>(a, getNext(), this);
            getNext().setPrev(node);
            setNext(node);
        }

        public A shift() {
            A a = getNext().getValue();
            getNext().getNext().setPrev(this);
            setNext(getNext().getNext());
            return a;
        }
    }

    public static class TailNode<A> extends Node<A> {
        public TailNode(Node<A> prev) {
            super(null, null, prev);
        }

        @Override
        public A getValue() {
            throw new NoSuchElementException("tail empty");
        }

        @Override
        public Node<A> getNext() {
            throw new NoSuchElementException("tail empty");
        }

        @Override
        public void setNext(Node<A> next) {
            throw new NoSuchElementException("tail empty");
        }

        @Override
        public boolean isEmpty() {
            return true;
        }

        public void push(A a) {
            Node<A> node = new Node<>(a, this, getPrev());

            getPrev().setNext(node);
            setPrev(node);
        }

        public A pop() {
            A a = getPrev().getValue();

            getPrev().getPrev().setNext(this);
            setPrev(getPrev().getPrev());
            return a;
        }
    }
}
