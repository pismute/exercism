use std::iter::FromIterator;

struct Node<T> {
    data: T,
    next: Option<Box<Node<T>>>,
}

pub struct SimpleLinkedList<T> {
    head: Option<Box<Node<T>>>,
}

impl<T> SimpleLinkedList<T> {
    pub fn new() -> Self {
        Self { head: None }
    }

    // You may be wondering why it's necessary to have is_empty()
    // when it can easily be determined from len().
    // It's good custom to have both because len() can be expensive for some types,
    // whereas is_empty() is almost always cheap.
    // (Also ask yourself whether len() is expensive for SimpleLinkedList)
    pub fn is_empty(&self) -> bool {
        self.head.is_none()
    }

    pub fn len(&self) -> usize {
        let mut head = &self.head;
        let mut count = 0;

        while let Some(Node { next, .. }) = head.as_deref() {
            count += 1;
            head = next;
        }

        count
    }

    pub fn push(&mut self, element: T) {
        self.head = Some(Box::new(Node {
            data: element,
            next: self.head.take(),
        }));
    }

    pub fn pop(&mut self) -> Option<T> {
        match self.head.take() {
            None => None,
            Some(boxed) => {
                let Node { data, next } = *boxed;
                self.head = next;
                Some(data)
            }
        }
    }

    pub fn peek(&self) -> Option<&T> {
        self.head.as_deref().map(|x| &x.data)
    }

    #[must_use]
    pub fn rev(mut self) -> SimpleLinkedList<T> {
        let mut new = Self::new();

        while let Some(e) = self.pop() {
            new.push(e);
        }

        new
    }
}

impl<T> FromIterator<T> for SimpleLinkedList<T> {
    fn from_iter<I: IntoIterator<Item = T>>(iter: I) -> Self {
        let mut new = Self::new();

        for e in iter {
            new.push(e);
        }

        new
    }
}

pub struct IntoIter<T> {
    head: Option<Box<Node<T>>>,
}

impl<T> Iterator for IntoIter<T> {
    type Item = T;

    #[inline]
    fn next(&mut self) -> Option<Self::Item> {
        self.head.take().map(|x| {
            let Node { data, next } = *x;
            self.head = next;
            data
        })
    }
}

impl<T> IntoIterator for SimpleLinkedList<T> {
    type Item = T;

    type IntoIter = IntoIter<Self::Item>;

    #[inline]
    fn into_iter(self) -> Self::IntoIter {
        IntoIter {
            head: self.rev().head,
        }
    }
}

// In general, it would be preferable to implement IntoIterator for SimpleLinkedList<T>
// instead of implementing an explicit conversion to a vector. This is because, together,
// FromIterator and IntoIterator enable conversion between arbitrary collections.
// Given that implementation, converting to a vector is trivial:
//
// let vec: Vec<_> = simple_linked_list.into_iter().collect();
//
// The reason this exercise's API includes an explicit conversion to Vec<T> instead
// of IntoIterator is that implementing that interface is fairly complicated, and
// demands more of the student than we expect at this point in the track.

impl<T> From<SimpleLinkedList<T>> for Vec<T> {
    fn from(linked_list: SimpleLinkedList<T>) -> Vec<T> {
        linked_list.into_iter().collect()
    }
}
