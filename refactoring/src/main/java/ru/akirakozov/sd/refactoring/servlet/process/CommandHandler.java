package ru.akirakozov.sd.refactoring.servlet.process;

public class CommandHandler {
    public Process getProcess(final String command) {
        switch (command) {
            case "max": {
                return new MaxPriceProcess();
            }
            case "min": {
                return new MinPriceProcess();
            }
            case "sum": {
                return new SumPricesProcess();
            }
            case "count": {
                return new CountPricesProcess();
            }
        }

        return () -> "Unknown command: " + command;
    }
}
