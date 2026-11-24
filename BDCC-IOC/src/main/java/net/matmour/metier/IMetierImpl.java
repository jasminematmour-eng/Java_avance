/*package net.matmour.metier;

import net.matmour.dao.IDao;
import net.matmour.ext.DaoImplV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
@Component
@Qualifier("dao")
//@Service("metier")
public class IMetierImpl implements IMetier {
    @Autowired
    //@Qualifier("d2")
    private IDao dao;

    public IMetierImpl(){
    this.dao = dao;
}

    public IMetierImpl(DaoImplV2 daoImplV2) {
    }


    @Override
    public double calcul() {
        double t = dao.getData();
        double res = t*12 *Math.PI/2 *Math.cos(t);
        return res;
    }

    public void setDao(IDao dao) {
        this.dao = dao;
    }
}*/
package net.matmour.metier;

import net.matmour.dao.IDao;

public class IMetierImpl implements IMetier {

    private IDao dao;

    // Constructeur utilisé par Spring XML
    public IMetierImpl(IDao dao) {
        this.dao = dao;
    }

    @Override
    public double calcul() {
        double t = dao.getData();
        double res = t * 12 * Math.PI / 2 * Math.cos(t);
        return res;
    }

    // Setter (optionnel)
    public void setDao(IDao dao) {
        this.dao = dao;
    }
}

