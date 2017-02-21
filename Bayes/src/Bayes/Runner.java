package Bayes;

import java.io.IOException;
import java.util.List;

/**
 * Created by max on 19.02.17.
 */
public class Runner {
    public static void main(String[] args) throws IOException {
        BayesNode bayesNode1 = new BayesNode("предоставляю услуги бухгалтера" , "SPAM");
        BayesNode bayesNode4 = new BayesNode("предоставляю возможность виагру" , "SPAM");
        BayesNode bayesNode2 = new BayesNode("спешите купить виагру" , "SPAM");
        BayesNode bayesNode3 = new BayesNode("надо купить молоко" , "HAM");
        BayesWork bayesWork = new BayesWork();
        bayesWork.addCloud(bayesNode1);
        bayesWork.addCloud(bayesNode2);
        bayesWork.addCloud(bayesNode3);
//        bayesWork.addCloud(bayesNode4);

        System.out.println(bayesWork.showSpamMap());
        String [ ] test = new String[]{"надо","купить","сигареты"};
        bayesWork.show();
        System.out.println(bayesWork.evaluateHam("надо купить сигареты"));
        System.out.println(bayesWork.evaluateSpam("надо купить сигареты"));


    }
}
