/*
  Why is iterator's signiture different? `&mut self` vs `self` vs `&self`:

  * fn next(&mut self)
  * fn count(self)
  * fn size_hint(&self)
*/

fn is_silence(x: &str) -> bool {
    x.is_empty()
}

fn is_yell(x: &str) -> bool {
    let mut y = x.chars().filter(|z| z.is_alphabetic());

    y.any(|z| z.is_uppercase()) && !y.any(|z| z.is_lowercase())
}

fn is_question(x: &str) -> bool {
    x.ends_with("?")
}

pub fn reply(message: &str) -> &str {
    let x = message.trim();

    if is_silence(&x) {
        "Fine. Be that way!"
    } else if is_question(&x) {
        if is_yell(&x) {
            "Calm down, I know what I'm doing!"
        } else {
            "Sure."
        }
    } else if is_yell(&x) {
        "Whoa, chill out!"
    } else {
        "Whatever."
    }
}
