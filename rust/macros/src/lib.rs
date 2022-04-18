#[macro_export]
macro_rules! hashmap {
    (@unit $x:expr) => { () };
    (@count $($x:expr),*) => { <[()]>::len(&[$(macros::hashmap!(@unit $x)),*]) };

    () => { ::std::collections::HashMap::new()};
    (,) => { compile_error!("don't allow only comma") };
    ( $( $x:expr => $y:expr ),* $(,)?) => {
        {
            let size = macros::hashmap!(@count $($x),*);
            let mut temp_hash_map = ::std::collections::HashMap::with_capacity(size);
            $(
                temp_hash_map.insert($x, $y);
            )*
            temp_hash_map
        }
    };
}

/*
This is a hack to avoid a problem:

error: cannot find macro `hashmap` in this scope
  --> tests/macros.rs:79:30
   |
79 |         let _without_comma = macros::hashmap!(23=> 623, 34 => 21);
   |                              ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
   |

When it is used without a import statements. hashmap!() can't call itself without namespaces. This macro is exposed in two ways of macros::hasmap and macros::macros::hashmap --;
*/
pub mod macros {
    pub use super::hashmap;
}
