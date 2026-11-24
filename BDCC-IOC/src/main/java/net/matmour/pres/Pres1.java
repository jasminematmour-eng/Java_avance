package net.matmour.pres;
import net.matmour.dao.IDao;
import net.matmour.dao.DaoImpl;
//import net.matmour.ext.DaoImplV2;
import net.matmour.ext.DaoImplV2;
import net.matmour.metier.IMetierImpl;

public class Pres1 {
    public static void main(String[] args) {
        DaoImplV2 d =  new DaoImplV2() ;
        IMetierImpl metier = new IMetierImpl(d);
        metier.setDao(d);
        System.out.println("res ="+metier.calcul());

    }
}
