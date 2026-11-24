package net.matmour.ext;

import net.matmour.dao.IDao;
import org.springframework.stereotype.Repository;

@Repository("d2")
public class DaoImplV2 implements IDao {
    @Override
    public double getData() {
        System.out.println("version capteurs ...");
        double t = 12;
        return t;
    }
}
