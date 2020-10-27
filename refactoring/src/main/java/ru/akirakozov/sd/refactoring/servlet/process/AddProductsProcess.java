package ru.akirakozov.sd.refactoring.servlet.process;

public class AddProductsProcess extends BaseProcess {
    String name;
    int price;

    public AddProductsProcess(String name, int price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public String process() {
        try {
            db.updateQuery("INSERT INTO PRODUCT (NAME, PRICE) VALUES (\"" + name + "\"," + price + ")");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return builder.toString();
    }
}
