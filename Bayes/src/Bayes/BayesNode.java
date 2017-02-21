package Bayes;

/**
 * Created by max on 19.02.17.
 */


//        val bestClass = c.classifier.classify("надо купить сигареты")

public class BayesNode {
    private String cloud;
    private String result;


    public BayesNode(String cloud, String result) {
        this.cloud = cloud;
        this.result = result;
    }

    public String getCloud() {
        return cloud;
    }

    public String getResult() {
        return result;
    }


}
