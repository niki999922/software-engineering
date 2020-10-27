package ru.akirakozov.sd.refactoring.servlet;

public class HTMLBuilder {
    private static final String HTML_START = "<html>";
    private static final String HTML_END = "</html>";
    private static final String BODY_START = "<body>";
    private static final String BODY_END = "</body>";

    private final StringBuilder dom = new StringBuilder();

    HTMLBuilder add(String str, String lastCharacter) {
        dom.append(str).append(lastCharacter);
        return this;
    }

    HTMLBuilder add(String str) {
        add(str, "\n");
        return this;
    }

    HTMLBuilder add(int value, String lastCharacter) {
        dom.append(value).append(lastCharacter);
        return this;
    }

    HTMLBuilder add(int value) {
        add(value, "\n");
        return this;
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
