use std::collections::HashMap;

#[derive(Clone, PartialEq, Debug)]
pub struct Edge {
    from: String,
    to: String,
    attrs: HashMap<String, String>,
}

impl Edge {
    pub fn new(from: &str, to: &str) -> Edge {
        Edge {
            from: from.to_string(),
            to: to.to_string(),
            attrs: HashMap::new(),
        }
    }

    pub fn with_attrs(mut self, attrs: &[(&str, &str)]) -> Self {
        for (name, value) in attrs {
            self.attrs.insert(name.to_string(), value.to_string());
        }
        self
    }
}

#[derive(Clone, PartialEq, Debug)]
pub struct Node {
    pub id: String,
    attrs: HashMap<String, String>,
}

impl Node {
    pub fn new(id: &str) -> Node {
        Node {
            id: id.to_string(),
            attrs: HashMap::new(),
        }
    }

    pub fn with_attrs(mut self, attrs: &[(&str, &str)]) -> Self {
        for (name, value) in attrs {
            self.attrs.insert(name.to_string(), value.to_string());
        }
        self
    }

    pub fn get_attr(&self, attr_name: &str) -> Option<&str> {
        self.attrs.get(attr_name).map(|x| x.as_str())
    }
}

pub struct Graph {
    pub nodes: Vec<Node>,
    pub edges: Vec<Edge>,
    pub attrs: HashMap<String, String>,
}

impl Graph {
    pub fn new() -> Self {
        Graph {
            nodes: vec![],
            edges: vec![],
            attrs: HashMap::new(),
        }
    }

    pub fn with_nodes(mut self, nodes: &[Node]) -> Self {
        for node in nodes {
            self.nodes.push(node.clone());
        }

        self
    }

    pub fn with_edges(mut self, edges: &[Edge]) -> Self {
        for edge in edges {
            self.edges.push(edge.clone());
        }
        self
    }

    pub fn with_attrs(mut self, attrs: &[(&str, &str)]) -> Self {
        for (name, value) in attrs {
            self.attrs.insert(name.to_string(), value.to_string());
        }

        self
    }

    pub fn get_node(&self, id: &str) -> Option<&Node> {
        self.nodes.iter().find(|x| x.id == id)
    }
}

pub mod graph {
    pub use super::Graph;
    pub mod graph_items {
        pub mod edge {
            pub use super::super::super::Edge;
        }
        pub mod node {
            pub use super::super::super::Node;
        }
    }
}
