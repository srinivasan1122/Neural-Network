import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class NeuralNetwork {
    private int numLayers;
    private int[] numNodes;
    private Map<Connection, Double> weights;

    public NeuralNetwork(int numLayers, int[] numNodes) {
        this.numLayers = numLayers;
        this.numNodes = numNodes;
        this.weights = new HashMap<>();

        setWeights();
    }

    private void setWeights() {
        Scanner scanner = new Scanner(System.in);

        for (int i = 0; i < numLayers - 1; i++) {
            for (int node1 = 0; node1 < numNodes[i]; node1++) {
                for (int node2 = 0; node2 < numNodes[i + 1]; node2++) {
                    System.out.print("Enter weight for connection from layer " + i + ", node " + node1 +
                            " to layer " + (i + 1) + ", node " + node2 + ": ");
                    double weight = scanner.nextDouble();
                    weights.put(new Connection(i, node1, i + 1, node2), weight);
                }
            }
        }
    }

    public double getWeight(int layerFrom, int nodeFrom, int layerTo, int nodeTo) {
        Connection connection = new Connection(layerFrom, nodeFrom, layerTo, nodeTo);
        return weights.getOrDefault(connection, 0.0);
    }
}

class Connection {
    private int layerFrom, nodeFrom, layerTo, nodeTo;

    public Connection(int layerFrom, int nodeFrom, int layerTo, int nodeTo) {
        this.layerFrom = layerFrom;
        this.nodeFrom = nodeFrom;
        this.layerTo = layerTo;
        this.nodeTo = nodeTo;
    }

    @Override
    public int hashCode() {
        return (layerFrom * 31 + nodeFrom) * 31 + (layerTo * 31 + nodeTo);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Connection connection = (Connection) obj;
        return layerFrom == connection.layerFrom && nodeFrom == connection.nodeFrom && layerTo == connection.layerTo && nodeTo == connection.nodeTo;
    }
}

public class NeuralNetworkApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of layers in the neural network: ");
        int numLayers = scanner.nextInt();

        int[] numNodes = new int[numLayers];
        for (int i = 0; i < numLayers; i++) {
            System.out.print("Enter the number of nodes in layer " + i + ": ");
            numNodes[i] = scanner.nextInt();
        }

        NeuralNetwork network = new NeuralNetwork(numLayers, numNodes);

        System.out.print("Enter the source layer: ");
        int layerFrom = scanner.nextInt();
        System.out.print("Enter the source node: ");
        int nodeFrom = scanner.nextInt();
        System.out.print("Enter the destination layer: ");
        int layerTo = scanner.nextInt();
        System.out.print("Enter the destination node: ");
        int nodeTo = scanner.nextInt();

        double weight = network.getWeight(layerFrom, nodeFrom, layerTo, nodeTo);
        System.out.println("Weight for edge from layer " + layerFrom + ", node " + nodeFrom +
                " to layer " + layerTo + ", node " + nodeTo + ": " + weight);
    }
}
