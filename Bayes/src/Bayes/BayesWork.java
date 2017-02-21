package Bayes;

import com.sun.org.apache.xpath.internal.SourceTree;
import com.sun.xml.internal.messaging.saaj.util.SAAJUtil;

import java.io.IOException;
import java.util.*;

/**
 * Created by max on 19.02.17.
 */
public class BayesWork {
    private Map<String, String> spamMap = new HashMap<>();
    private Map<String, String> hamMap = new HashMap<>();
    public Map<String, Integer> spamWords = new HashMap<>();
    public Map<String, Integer> hamWords = new HashMap<>();
    private int countSpamWords = 0;
    private int classSpamWords;
    private int countHamWords= 0;
    private int classHamWords;

    public BayesWork() {
        classSpamWords = spamWords.size();
        classHamWords = hamWords.size();
    }

    private void addWordInMap(Map<String, Integer> map, String[] string) {
        for (String str : string) {
            map.compute(str, (k, v) -> v == null ? 1 : v + 1);
        }

    }

    //считаем все слова в спаме
    public int countSpamWords() {
        countSpamWords = 0;
        spamWords.forEach((k, v) -> countSpamWords += v);
        return countSpamWords;
    }

    public int countHamWords() {
        countHamWords = 0;
        hamWords.forEach((k, v) -> countHamWords += v);
        return countHamWords;
    }

    public void addCloud(BayesNode node) throws IOException {
        String[] sentence = node.getCloud().split(" ");
        if (node.getResult() == "SPAM") {
            spamMap.put(node.getCloud(), node.getResult());
            addWordInMap(spamWords, sentence);
        } else if (node.getResult() == "HAM") {
            hamMap.put(node.getCloud(), node.getResult());
            addWordInMap(hamWords, sentence);
        } else if (node.getResult() == "CHECK") {
//            checkClouds(sentence , node);
        } else throw new IOException("you should read the manual better");
    }

    public int showSpamMap() {
        return spamMap.size();
    }


//    @Override
//    public void toString() {
//        spamWords.forEach( (k,v) ->  k,v

    public void show() {
        System.out.println("showSpamWords");
        spamWords.forEach((k, v) -> System.out.println(k + " " + "[" + v + "]"));
        System.out.println("--------------");
        System.out.println("showHamWords");
        hamWords.forEach((k, v) -> System.out.println(k + " " + "[" + v + "]"));

    }

    private Float checkNullOrNot(Map<String, Integer> list , String string ){
        if (list.get(string) == null) return (float) 0.0;
        return Float.valueOf(list.get(string));
    }

    private Float countUniqueWords(){
        Set<String> set =  new HashSet<>();
        set.addAll(spamWords.keySet());
        set.addAll(hamWords.keySet());
        return (float)set.size();
    }

    private List<Double> checkClouds(String stuff) {
        String [ ] strings = stuff.split(" ") ;
        List<Double> array = new ArrayList<>();
        Double checkSpam = Math.log(((float) spamMap.size() /((float) spamMap.size() + (float) hamMap.size())));
        Double checkHam = Math.log((float) hamMap.size() / ((float) spamMap.size() +(float) hamMap.size()));
        //for SPAM and for HAM
        for (String string : strings) {
            checkSpam += Math.log((1.0 +  (float) checkNullOrNot(spamWords ,string ))
                    / (countUniqueWords() + (float) countSpamWords()));
//                    / ( (float) spamWords.size() + (float) hamWords.size() + (float) countSpamWords()));

            checkHam += Math.log((1.0 + checkNullOrNot( hamWords , string))
                    / (countUniqueWords() + (float) countHamWords()));
//                    / (float) (spamWords.size() + (float) hamWords.size() + (float) countHamWords()));
        }
        array.add(checkSpam);
        array.add(checkHam);
        return array;
    }

    public Double evaluateSpam( String  stuff   ){
          List<Double> array =  checkClouds(stuff);
          return Math.exp(array.get(0))/ (Math.exp(array.get(0)) + Math.exp(array.get(1)));
    }
     public Double evaluateHam( String  strings  ){
          return 1.0 - evaluateSpam(strings);
     }
}
