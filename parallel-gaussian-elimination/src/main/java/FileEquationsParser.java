import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileEquationsParser {

    private FileEquationsParser() {
    }

    public static SystemLinearEquations readEquationFromFile(String path) throws IOException {
        int size;
        double[][] leftMatrix;
        double[] rightMatrix;
        try (BufferedReader in = new BufferedReader(new FileReader(new File(path)))) {

            String line = in.readLine();

            size = Integer.parseInt(line);
            leftMatrix = new double[size][size];
            rightMatrix = new double[size];

            for (int i = 0; i < size; i++) {

                String[] sp = in.readLine().split(" ");

                for (int j = 0; j < size; j++) {
                    leftMatrix[i][j] = Double.parseDouble(sp[j]);
                }
            }
            String[] sp = in.readLine().split(" ");

            for (int j = 0; j < size; j++) {
                rightMatrix[j] = Double.parseDouble(sp[j]);
            }
        }
        return new SystemLinearEquations(size, leftMatrix, rightMatrix);
    }

}
