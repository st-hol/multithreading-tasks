import multithreading.MyConcurrentScheduleRunner;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    private static final Logger LOG = Logger.getLogger(Main.class.getName());

    private static final String SOURCE_FILE_PATH = "E:\\KPI\\Parallel calculations -1\\multithreading-tasks\\" +
            "parallel-gaussian-elimination\\src\\main\\resources\\test3x3.txt";
    private static final String OUTPUT_PATH = "E:\\KPI\\Parallel calculations -1\\multithreading-tasks\\" +
            "parallel-gaussian-elimination\\src\\main\\resources\\";

    public static void main(String[] args) throws IOException {
        SystemLinearEquations systemLinearEquations = FileEquationsParser.readEquationFromFile(SOURCE_FILE_PATH);

        LOG.log(Level.INFO, "System linear equations before solving:\n");
        systemLinearEquations.printLeftMatrix();
        systemLinearEquations.printRightMatrix();

        EliminationMethodEquationsSolver solver =
                new EliminationMethodEquationsSolver(systemLinearEquations, new MyConcurrentScheduleRunner());
        solver.parallelGaussianElimination();

        LOG.log(Level.INFO, "System linear equations after solving:\n");
        systemLinearEquations.printLeftMatrix();
        systemLinearEquations.printRightMatrix();

        systemLinearEquations.writeResultsToFile(OUTPUT_PATH);
    }


}
