#[derive(Debug)]
pub enum CalculatorInput {
    Add,
    Subtract,
    Multiply,
    Divide,
    Value(i32),
}

impl CalculatorInput {
    fn calc(&self, l: i32, r: i32) -> Option<i32> {
        use CalculatorInput::*;
        match self {
            Add => Some(l + r),
            Subtract => Some(l - r),
            Multiply => Some(l * r),
            Divide => Some(l / r),
            _ => None,
        }
    }
}

pub fn evaluate(inputs: &[CalculatorInput]) -> Option<i32> {
    use CalculatorInput::*;

    inputs
        .iter()
        .try_fold(Vec::<i32>::new(), |mut acc, x| match x {
            Value(v) => {
                acc.push(*v);
                Some(acc)
            }
            op => acc
                .pop()
                .zip(acc.pop())
                .and_then(|(r, l)| op.calc(l, r))
                .map(|v| {
                    acc.push(v);
                    acc
                }),
        })
        .and_then(|mut xs| if xs.len() == 1 { xs.pop() } else { None })
}
