#![feature(fn_traits)]

use std::collections::{HashMap, HashSet};

/// `InputCellId` is a unique identifier for an input cell.
#[derive(Clone, Copy, Debug, PartialEq, Eq, Hash)]
pub struct InputCellId(usize);
/// `ComputeCellId` is a unique identifier for a compute cell.
/// Values of type `InputCellId` and `ComputeCellId` should not be mutually assignable,
/// demonstrated by the following tests:
///
/// ```compile_fail
/// let mut r = react::Reactor::new();
/// let input: react::ComputeCellId = r.create_input(111);
/// ```
///
/// ```compile_fail
/// let mut r = react::Reactor::new();
/// let input = r.create_input(111);
/// let compute: react::InputCellId = r.create_compute(&[react::CellId::Input(input)], |_| 222).unwrap();
/// ```
#[derive(Clone, Copy, Debug, PartialEq, Eq, Hash)]
pub struct ComputeCellId(usize);
#[derive(Clone, Copy, Debug, PartialEq, Eq, Hash)]
pub struct CallbackId(usize);

#[derive(Clone, Copy, Debug, PartialEq, Eq, Hash)]
pub enum CellId {
    Input(InputCellId),
    Compute(ComputeCellId),
}

impl CellId {
    pub fn is_input(&self) -> bool {
        self.as_input().is_some()
    }

    pub fn as_compute(&self) -> Option<&ComputeCellId> {
        match self {
            CellId::Compute(x) => Some(x),
            _ => None,
        }
    }

    pub fn as_input(&self) -> Option<&InputCellId> {
        match self {
            CellId::Input(x) => Some(x),
            _ => None,
        }
    }
}

#[derive(Debug, PartialEq)]
pub enum RemoveCallbackError {
    NonexistentCell,
    NonexistentCallback,
}

struct InputCell<T> {
    value: T,
}

struct ComputeCell<'a, T> {
    dependencies: Vec<CellId>,
    func: Box<dyn Fn(&[T]) -> T + 'a>,
    callback_ids: HashSet<CallbackId>,
}

type Callback<'a, T> = Box<dyn FnMut(T) + 'a>;

pub struct Reactor<'a, T> {
    id_offset: usize,
    input_cells: HashMap<InputCellId, InputCell<T>>,
    compute_cells: HashMap<ComputeCellId, ComputeCell<'a, T>>,
    callbacks: HashMap<CallbackId, Callback<'a, T>>,
    deps: HashMap<CellId, HashSet<ComputeCellId>>, // left one is used by right one
}

// You are guaranteed that Reactor will only be tested against types that are Copy + PartialEq.
impl<'a, T: Copy + PartialEq + std::fmt::Debug> Reactor<'a, T> {
    pub fn new() -> Self {
        Reactor {
            id_offset: 0,
            input_cells: HashMap::new(),
            compute_cells: HashMap::new(),
            callbacks: HashMap::new(),
            deps: HashMap::new(),
        }
    }

    // Creates an input cell with the specified initial value, returning its ID.
    pub fn create_input(&mut self, initial: T) -> InputCellId {
        let id = InputCellId(self.id_offset);
        self.id_offset += 1;

        let value = InputCell { value: initial };

        self.input_cells.insert(id, value);
        id
    }

    // Creates a compute cell with the specified dependencies and compute function.
    // The compute function is expected to take in its arguments in the same order as specified in
    // `dependencies`.
    // You do not need to reject compute functions that expect more arguments than there are
    // dependencies (how would you check for this, anyway?).
    //
    // If any dependency doesn't exist, returns an Err with that nonexistent dependency.
    // (If multiple dependencies do not exist, exactly which one is returned is not defined and
    // will not be tested)
    //
    // Notice that there is no way to *remove* a cell.
    // This means that you may assume, without checking, that if the dependencies exist at creation
    // time they will continue to exist as long as the Reactor exists.
    pub fn create_compute<F: Fn(&[T]) -> T + 'a>(
        &mut self,
        dependencies: &[CellId],
        compute_func: F,
    ) -> Result<ComputeCellId, CellId> {
        match dependencies.iter().find(|x| match x {
            CellId::Input(y) => !self.input_cells.contains_key(y),
            CellId::Compute(y) => !self.compute_cells.contains_key(y),
        }) {
            Some(x) => Err(*x),
            None => {
                // new id
                let id = ComputeCellId(self.id_offset);
                self.id_offset += 1;

                // add dependencies
                for x in dependencies.iter() {
                    self.deps
                        .entry(*x)
                        .and_modify(|xs| {
                            xs.insert(id);
                        })
                        .or_insert_with(|| HashSet::from([id]));
                }

                // new compute
                let value = ComputeCell {
                    dependencies: dependencies.to_vec(),
                    func: Box::new(compute_func),
                    callback_ids: HashSet::new(),
                };

                self.compute_cells.insert(id, value);

                Ok(id)
            }
        }
    }

    // Retrieves the current value of the cell, or None if the cell does not exist.
    //
    // You may wonder whether it is possible to implement `get(&self, id: CellId) -> Option<&Cell>`
    // and have a `value(&self)` method on `Cell`.
    //
    // It turns out this introduces a significant amount of extra complexity to this exercise.
    // We chose not to cover this here, since this exercise is probably enough work as-is.
    pub fn value(&self, id: CellId) -> Option<T> {
        match id {
            CellId::Input(x) => self.input_cells.get(&x).map(|y| y.value),
            CellId::Compute(x) => self.compute_value_by_id(&HashMap::new(), x),
        }
    }

    fn compute_value_by_id(&self, cache: &HashMap<CellId, T>, id: ComputeCellId) -> Option<T> {
        self.compute_cells
            .get(&id)
            .map(|x| self.compute_value(cache, x))
    }

    fn compute_value(&self, cache: &HashMap<CellId, T>, cell: &ComputeCell<T>) -> T {
        let args: Vec<T> = cell
            .dependencies
            .iter()
            .filter_map(|x| cache.get(x).copied().or_else(|| self.value(*x)))
            .collect();
        cell.func.call((args.as_slice(),))
    }

    // collect compute cell ids of one level.
    fn next_compute_ids(&self, ids: &[CellId]) -> HashSet<ComputeCellId> {
        ids.iter()
            .flat_map(|x| self.deps.get(x).into_iter())
            .flat_map(|xs| xs.iter())
            .copied()
            .collect()
    }

    // Sets the value of the specified input cell.
    //
    // Returns false if the cell does not exist.
    //
    // Similarly, you may wonder about `get_mut(&mut self, id: CellId) -> Option<&mut Cell>`, with
    // a `set_value(&mut self, new_value: T)` method on `Cell`.
    //
    // As before, that turned out to add too much extra complexity.
    pub fn set_value(&mut self, id: InputCellId, new_value: T) -> bool {
        let mut values: HashMap<CellId, T> = HashMap::new();
        let empty_values: HashMap<CellId, T> = HashMap::new();

        if self.input_cells.get(&id).is_some() {
            let input_id = CellId::Input(id);
            values.insert(input_id, new_value);

            let mut compute_ids: HashSet<ComputeCellId> = self.next_compute_ids(&[input_id]);

            while !compute_ids.is_empty() {
                let mut next_ids: Vec<CellId> = Vec::new();

                for compute_id in compute_ids {
                    if let Some(cell) = self.compute_cells.get(&compute_id) {
                        let new = self.compute_value(&values, cell);
                        let old = self.compute_value(&empty_values, cell);

                        if new != old {
                            let cell_id = CellId::Compute(compute_id);
                            values.insert(cell_id, new);
                            next_ids.push(cell_id);
                        }
                    }
                }

                compute_ids = self.next_compute_ids(next_ids.as_slice());
            }
        }

        // set value and call callbacks changed
        for (id, value) in values.iter() {
            match id {
                CellId::Input(x) => {
                    if let Some(input_cell) = self.input_cells.get_mut(x) {
                        input_cell.value = *value;
                    }
                }
                CellId::Compute(x) => {
                    if let Some(cell) = self.compute_cells.get(x) {
                        for callback_id in cell.callback_ids.iter() {
                            if let Some(callback) = self.callbacks.get_mut(&callback_id) {
                                callback.call_mut((*value,))
                            }
                        }
                    }
                }
            }
        }

        !values.is_empty()
    }

    // Adds a callback to the specified compute cell.
    //
    // Returns the ID of the just-added callback, or None if the cell doesn't exist.
    //
    // Callbacks on input cells will not be tested.
    //
    // The semantics of callbacks (as will be tested):
    // For a single set_value call, each compute cell's callbacks should each be called:
    // * Zero times if the compute cell's value did not change as a result of the set_value call.
    // * Exactly once if the compute cell's value changed as a result of the set_value call.
    //   The value passed to the callback should be the final value of the compute cell after the
    //   set_value call.
    pub fn add_callback<F: FnMut(T) + 'a>(
        &mut self,
        id: ComputeCellId,
        callback: F,
    ) -> Option<CallbackId> {
        self.compute_cells.get_mut(&id).map(|x| {
            let id_offset = self.id_offset;
            self.id_offset += 1;

            let callback_id = CallbackId(id_offset);
            self.callbacks.insert(callback_id, Box::new(callback));
            x.callback_ids.insert(callback_id);
            callback_id
        })
    }

    // Removes the specified callback, using an ID returned from add_callback.
    //
    // Returns an Err if either the cell or callback does not exist.
    //
    // A removed callback should no longer be called.
    pub fn remove_callback(
        &mut self,
        cell: ComputeCellId,
        callback: CallbackId,
    ) -> Result<(), RemoveCallbackError> {
        self.compute_cells
            .get_mut(&cell)
            .ok_or_else(|| RemoveCallbackError::NonexistentCell)
            .and_then(|x| {
                self.callbacks
                    .remove(&callback)
                    .filter(|_| x.callback_ids.remove(&callback))
                    .ok_or_else(|| RemoveCallbackError::NonexistentCallback)
                    .map(|_| ())
            })
    }
}
