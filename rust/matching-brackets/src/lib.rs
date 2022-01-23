const BRACKET_OPEN: char = '[';
const BRACKET_CLOSE: char = ']';
const BRACE_OPEN: char = '{';
const BRACE_CLOSE: char = '}';
const PAREN_OPEN: char = '(';
const PAREN_CLOSE: char = ')';

pub fn brackets_are_balanced(string: &str) -> bool {
    string
        .chars()
        .try_fold(Vec::new(), |mut acc, x| match x {
            BRACKET_OPEN => {
                acc.push(BRACKET_CLOSE);
                Some(acc)
            }
            BRACE_OPEN => {
                acc.push(BRACE_CLOSE);
                Some(acc)
            }
            PAREN_OPEN => {
                acc.push(PAREN_CLOSE);
                Some(acc)
            }
            xx @ (BRACKET_CLOSE | BRACE_CLOSE | PAREN_CLOSE) => {
                acc.pop().filter(|c| *c == xx).map(|_| acc)
            }
            _ => Some(acc),
        })
        .map(|x| x.is_empty())
        .unwrap_or(false)
}
