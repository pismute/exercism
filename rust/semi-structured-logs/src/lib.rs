use strum_macros::IntoStaticStr;

/// various log levels
#[derive(Clone, PartialEq, Debug, IntoStaticStr)]
pub enum LogLevel {
    Debug,
    Error,
    Info,
    Warning,
}

/// primary function for emitting logs
pub fn log(level: LogLevel, message: &str) -> String {
    let level_name: &'static str = level.into();

    format!("[{}]: {}", level_name.to_uppercase(), message)
}

pub fn info(message: &str) -> String {
    log(LogLevel::Info, message)
}

pub fn warn(message: &str) -> String {
    log(LogLevel::Warning, message)
}

pub fn error(message: &str) -> String {
    log(LogLevel::Error, message)
}
