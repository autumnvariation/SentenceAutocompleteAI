import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class GraphGenerator {
    private File wordlist;
    ArrayList<String> words;
    private static final HashSet<Node> graph = new HashSet<>();

    private void addGraph(File wordlist) {
        this.wordlist = wordlist;
        this.words = getWords();
        regenGraph();
    }
    private String getNextWord(String word, boolean random){
        for (Node node : graph) {
            if (Objects.equals(node.getNode(), word)){
                if (random){
                    return node.getRandomNextWord();
                }
                else{
                    return node.getClosestNode();
                }
            }
        }
        return null;
    }
    private void regenGraph() {
        HashMap<String, Node> stringToNodeMap = new HashMap<>();
        HashSet<Node> newNodes = new HashSet<>();
        for (Node node : graph) {
            stringToNodeMap.put(node.getNode(), node);
        }

        for (int i = 0; i < words.size() - 1; i++) {
            String currentWord = words.get(i);
            addNewNode(currentWord, stringToNodeMap, newNodes);

            Node currentNode = stringToNodeMap.get(currentWord);
            String nextWord = words.get(i + 1);
            addNewNode(nextWord, stringToNodeMap, newNodes);
            Node nextNode = stringToNodeMap.get(nextWord);

            currentNode.addEdgeNode(nextNode);
            currentNode.incrementEdgeNode(nextNode);
        }
        graph.addAll(newNodes);
    }
    private static void addNewNode(String currentWord, HashMap<String, Node> stringToNodeMap, HashSet<Node> newNodes) {
        if (stringToNodeMap.containsKey(currentWord)){
            return;
        }
        Node newNode = new Node(currentWord);
        stringToNodeMap.put(currentWord, newNode);
        newNodes.add(newNode);
    }

    private ArrayList<String> getWords() {
        ArrayList<String> words = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(wordlist);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String formattedText = formatText(line);
                if (formattedText.isBlank() || formattedText.split(" ").length == 1){
                    continue;
                }
                words.addAll(Arrays.asList(formattedText.split(" ")));
            }
        } catch (FileNotFoundException e) {
            //TODO: handle exception
        }
        return words;
    }
    private String formatText(String line) {
        return line
                .replaceAll("\\.", " . ")
                .replaceAll(",", " , ")
                .replaceAll(";", " ; ")
                .replaceAll(":", " : ")
                .replaceAll("\"", " \" ")
                .replaceAll("\\)", " ) ")
                .replaceAll("\\(", " ( ")
                .replaceAll("\\s+", " ");
    }
    private void printGraph(){
        for (Node node : graph) {
            System.out.println(node.getNode());
        }
    }
    public static void main(String[] args) {
        GraphGenerator graph = new GraphGenerator();
        File file = new File("res/sample_text/bee_movie.txt");
        graph.addGraph(file);
//        graph.printGraph();
        String nextWord = "How";
        for (int i = 0; i < 1000; i++) {
            System.out.print(nextWord + " ");
            nextWord = graph.getNextWord(nextWord, true);
        }
    }
}