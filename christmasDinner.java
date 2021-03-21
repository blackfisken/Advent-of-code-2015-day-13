import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class christmasDinner {
    public static void main(String[] args) {
        try {
            File myObj = new File("inputChristmasDinner.txt");
            Scanner myReader = new Scanner(myObj);

            ArrayList<String> listOfNames = new ArrayList<>();
            ArrayList<String> namesSendPermutation;
            ArrayList<String> namesNoPermutation;
            ArrayList<String> namesCalculateHappiness;
            HashMap<String, Integer> nameMapsHappiness = new HashMap<>();
            int max = 0;
            int sum;
            Boolean firstIteration = true;
            Boolean firstPerson = true;

            while (myReader.hasNextLine()) {
                String dataLine = myReader.nextLine();
                String[] words = dataLine.split(" ", 11);

                //Assign input data to variables
                String name1 = words[0];
                String gainOrLose = words[2];
                int valueHappiness = Integer.parseInt(words[3]);
                String name2 = words[10];
                name2 = name2.substring(0, name2.length() - 1); //remove dot in end of name

                //Create an array list containing the unique names. Later the list is used for permutations
                if (firstIteration) {
                    if (firstPerson) {
                        listOfNames.add(name1);
                        firstPerson = false;
                    }
                    if (!listOfNames.get(0).equals(name1)) {
                        firstIteration = false;
                    }
                    else listOfNames.add(name2);
                }

                //Check if happiness is lost or gained. If lose assign negative value
                if (gainOrLose.equals("lose")) {
                    valueHappiness = valueHappiness * -1;
                }

                //Create a hashmap. The key is name1 "sits beside" name2, and value is the happiness gained or loss
                nameMapsHappiness.put(name1 + name2, valueHappiness);
            }
            myReader.close();

            //Five names are going to be sent to permutation, the other three don't need to (explained in readme), iterate through the list of names.
            for (int a = 0; a < listOfNames.size(); a++) {
                for (int i = a; i < listOfNames.size() - 2; i++) {
                    Object[] twoLists = getListsPermutationOrNot(listOfNames, a, i);
                    namesNoPermutation = (ArrayList<String>) twoLists[0];
                    namesSendPermutation = (ArrayList<String>)twoLists[1];
                    List<List<String>> listsOfPermutationLists = listPermutations(namesSendPermutation); //Generate all possible permutations for the five names.

                    //For each list calculate the happiness and compare with max
                    for (List<String> oneListPermutation : listsOfPermutationLists) {  //For each list
                        namesCalculateHappiness = new ArrayList<>(namesNoPermutation); //Add list containing the names with no permutation
                        namesCalculateHappiness.addAll(oneListPermutation); //Add list containing the names with permutation
                        sum= calculateHappiness(namesCalculateHappiness, nameMapsHappiness); //Calculate the happiness by checking values in hashmap
                        max = Math.max(max, sum); //Compare if the calculated sum is the new max value
                    }
                }
            }
            System.out.println("Max: " + max);

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

    //If the first name in the list is A, the next is B, and so on,  A B C D E F G H.
    //Then we first let A sit between BC. In that case,  D E F G H is sent to calculate permutations
    //Next time we let A sit between BD, and the remaining names C E F G H is sent to calculate permutation
    //This is done until A have been sitting next to all possible combinations (BC BD BE BF BG BH | CD CE CF CG CH | DE DF DG DH | EF FG FH | FG FH | GH)
    public static Object[] getListsPermutationOrNot(ArrayList<String> listOfNames, int a, int i){
        ArrayList<String> namesSendPermutation = new ArrayList<>(listOfNames);
        ArrayList<String> namesNoPermutation = new ArrayList<>();
        namesNoPermutation.add(namesSendPermutation.get(0));
        namesSendPermutation.remove(0);
        namesNoPermutation.add(namesSendPermutation.get(a));
        namesSendPermutation.remove(a);
        namesNoPermutation.add(namesSendPermutation.get(i));
        namesSendPermutation.remove(i);

        return new Object[]{namesNoPermutation, namesSendPermutation};
    }

    //Compute the happiness by going through a list of names and add the happiness gained or lost for the two persons
    //Calculate happiness by adding the happiness gained or lost by sitting next to the person on your right and the happiness the person to your right is getting by sitting next to you
    public static int calculateHappiness(ArrayList<String> namesCalculateHappiness, HashMap<String, Integer> nameMapsHappiness){
        int sum=0;
        for (int j = 0; j < namesCalculateHappiness.size(); j++) {
            if (j == (namesCalculateHappiness.size() - 1)) {    //If last person in list, calculate happiness by taking the first person in list (round table)
                sum += nameMapsHappiness.get(namesCalculateHappiness.get(j) + namesCalculateHappiness.get(0));
                sum += nameMapsHappiness.get(namesCalculateHappiness.get(0) + namesCalculateHappiness.get(j));
            } else {
                sum += nameMapsHappiness.get(namesCalculateHappiness.get(j) + namesCalculateHappiness.get(j + 1));
                sum += nameMapsHappiness.get(namesCalculateHappiness.get(j + 1) + namesCalculateHappiness.get(j));
            }
        }
        return sum;
    }

    //Not my own code. I re-wrote this code from int to string https://stackoverflow.com/questions/24460480/permutation-of-an-arraylist-of-numbers-using-recursion
    public static List<List<String>> listPermutations(List<String> list) {

        if (list.size() == 0) {
            List<List<String>> result = new ArrayList<>();
            result.add(new ArrayList<>());
            return result;
        }

        List<List<String>> returnMe = new ArrayList<>();

        String firstElement = list.remove(0);

        List<List<String>> recursiveReturn = listPermutations(list);
        for (List<String> li : recursiveReturn) {

            for (int index = 0; index <= li.size(); index++) {
                List<String> temp = new ArrayList<>(li);
                temp.add(index, firstElement);
                returnMe.add(temp);
            }

        }
        return returnMe;
    }

}
