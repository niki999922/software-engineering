package ru.akirakozov.sd.refactoring.servlet;

public class HTMLBuilder {
    private static final String HTML_START = "<html>";
    private static final String HTML_END = "</html>";
    private static final String BODY_START = "<body>";
    private static final String BODY_END = "</body>";

    private final StringBuilder dom = new StringBuilder();

    void add(String str, String lastCharacter) {
        dom.append(str).append(lastCharacter);
    }

    void add(String str) {
        add(str, "\n");
    }

    void add(int value, String lastCharacter) {
        dom.append(value).append(lastCharacter);
    }

    void add(int value) {
        add(value, "\n");
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append(HTML_START + BODY_START + "\n")
                .append(dom.toString())
                .append(BODY_END + HTML_END);

        return stringBuilder.toString();
    }
}
