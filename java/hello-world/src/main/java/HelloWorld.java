public class HelloWorld {
  public static String hello(String name) {
    return name == null || name.isEmpty() ? "Hello, World!":
      "Hello, " + name + "!";
  }
}
