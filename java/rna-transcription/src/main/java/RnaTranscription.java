import java.util.HashMap;
import java.util.Map;

public class RnaTranscription {
    public static final Map<Character, Character> TRANSCRIPTION =
            new HashMap<Character, Character>() {{
                put('G', 'C');
                put('C', 'G');
                put('T', 'A');
                put('A', 'U');
            }};

    public static String ofDna(String dna) {
        return dna.chars()
                .mapToObj(i->(char)i)
                .map(TRANSCRIPTION::get)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                    .toString();
    }
}
