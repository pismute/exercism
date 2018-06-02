/*
  Why is iterator's signiture different? `&mut self` vs `self` vs `&self`:

  * fn next(&mut self)
  * fn count(self)
  * fn size_hint(&self)
*/

fn is_silence(x: &String) -> bool {
    x.is_empty()
}

fn is_yell(x: &String) -> bool {
    let y = x.chars().filter(|z| z.is_alphabetic());

    if y.is_empty() {
        false
    } else {
        y.all(|z| z.is_uppercase())
    }
}

fn is_question(x: &String) -> bool {
    x.ends_with("?")
}

pub fn reply(message: &str) -> &str {
    let mut x = String::from(message.trim());

    if is_silence(&x) {
        "Fine. Be that way!"
    } else if is_question(&x) {
        x.pop();

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
