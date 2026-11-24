package net.matmour.pres;

import net.matmour.metier.IMetier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class PresSpringAnnotation {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext("net.matmour");
        IMetier metier = applicationContext.getBean(IMetier.class);
        System.out.println("res="+metier.calcul());
    }
}
