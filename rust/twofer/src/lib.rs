pub fn twofer(name: &str)-> String {
    let you = Some(name).filter(|x| !x.is_empty()).unwrap_or("you");

    format!("One for {}, one for me.", you)
}
