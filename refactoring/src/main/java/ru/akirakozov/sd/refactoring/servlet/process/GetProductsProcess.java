package ru.akirakozov.sd.refactoring.servlet.process;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GetProductsProcess extends BaseProcess {
    @Override
    public String process() {
        try {
            db.executeQuery(builder, "SELECT * FROM PRODUCT", (ResultSet rs) -> {
                ArrayList<String> list = new ArrayList<>();

                try {
                    while (rs.next()) {
                        String name = rs.getString("name");
                        int price = rs.getInt("price");
                        list.add(name + "\t" + price + "</br>");
                    }
                } catch (SQLException ignore) {
                }

                return list;
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return builder.toString();
    }

}
