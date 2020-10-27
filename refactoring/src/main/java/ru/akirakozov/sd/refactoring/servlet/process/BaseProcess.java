package ru.akirakozov.sd.refactoring.servlet.process;

import ru.akirakozov.sd.refactoring.servlet.HTMLBuilder;
import ru.akirakozov.sd.refactoring.servlet.process.db.Database;

public abstract class BaseProcess implements Process {
    protected Database db;
    protected HTMLBuilder builder;

    BaseProcess() {
        db = new Database("jdbc:sqlite:test.db");
        builder = new HTMLBuilder();
    }
}
