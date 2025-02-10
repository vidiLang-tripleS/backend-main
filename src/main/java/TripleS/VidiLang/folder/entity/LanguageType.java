package TripleS.VidiLang.folder.entity;

public enum LanguageType {

    ENGLISH("English"),
    SPANISH("Spanish"),
    CHINESE("Chinese"),
    JAPANESE("Japanese"),
    PERSIAN("Persian"),
    RUSSIAN("Russian"),
    DEUTSCH("Deutsch"),
    FRENCH("French"),;

    private final String language;

    LanguageType(String language) {
        this.language = language;
    }
}
