package ru.akirakozov.sd.refactoring.servlet.process;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CountPricesProcess extends BaseProcess {
    @Override
    public String process() {
        builder.add("Number of products: ");

        try {
            db.executeQuery(builder, "SELECT COUNT(*) FROM PRODUCT", (ResultSet rs) -> {
                ArrayList<String> list = new ArrayList<>();

                try {
                    if (rs.next()) {
                        list.add(Integer.toString(rs.getInt(1)));
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
