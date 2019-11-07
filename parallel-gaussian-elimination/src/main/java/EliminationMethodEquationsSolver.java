import multithreading.AbstractProductionWithLockThread;
import multithreading.MyConcurrentScheduleRunner;

public class EliminationMethodEquationsSolver {

    private SystemLinearEquations systemLinearEquations;
    private MyConcurrentScheduleRunner scheduler;

    public EliminationMethodEquationsSolver(SystemLinearEquations systemLinearEquations, MyConcurrentScheduleRunner scheduler) {
        this.systemLinearEquations = systemLinearEquations;
        this.scheduler = scheduler;
    }

    public void parallelGaussianElimination() {

        for (int currentDiagonalIndex = 0; currentDiagonalIndex < systemLinearEquations.size; currentDiagonalIndex++) {
            swapIfCurrentDiagonalIndexIsZero(currentDiagonalIndex);
            double[] ratios = calculateRatioForRows(currentDiagonalIndex);
            subtract(currentDiagonalIndex, ratios);
        }

        for (int i = 0; i < systemLinearEquations.size; i++) {
            double value = systemLinearEquations.leftMatrix[i][i];
            systemLinearEquations.leftMatrix[i][i] = 1.0;
            systemLinearEquations.rightMatrix[i] /= value;
        }

    }

    private synchronized double[] calculateRatioForRows(int currentDiagonalIndex) {
        double[] ratios = new double[systemLinearEquations.size];

        for (int row = 0; row < systemLinearEquations.size; row++) {
            if (row == currentDiagonalIndex) continue;

            final int i = row;
            final int j = currentDiagonalIndex;

            scheduler.addThread(new AbstractProductionWithLockThread() {
                @Override
                public void run() {
                    super.run();
                    ratios[i] = systemLinearEquations.leftMatrix[i][j] / systemLinearEquations.leftMatrix[j][j];
                }
            });
        }

        scheduler.startAll();
        return ratios;
    }

    private void subtract(final int currentDiagonalIndex, double[] ratios) {

        for (int row = 0; row < systemLinearEquations.size; row++) {
            if (row == currentDiagonalIndex) continue;
            for (int column = 0; column < systemLinearEquations.size; column++) {

                final int i = row;
                final int j = column;

                scheduler.addThread(new AbstractProductionWithLockThread() {
                    @Override
                    public void run() {
                        super.run();
                        systemLinearEquations.leftMatrix[i][j] = systemLinearEquations.leftMatrix[i][j] -
                                ratios[i] * systemLinearEquations.leftMatrix[currentDiagonalIndex][j];
                    }
                });
            }

            final int row_f = row;

            scheduler.addThread(new AbstractProductionWithLockThread() {
                @Override
                public void run() {
                    super.run();
                    systemLinearEquations.rightMatrix[row_f] = systemLinearEquations.rightMatrix[row_f] -
                            ratios[row_f] * systemLinearEquations.rightMatrix[currentDiagonalIndex];
                }
            });
            scheduler.startAll();
        }
    }

    private void swapIfCurrentDiagonalIndexIsZero(int currentDiagonalIndex) {
        if (systemLinearEquations.leftMatrix[currentDiagonalIndex][currentDiagonalIndex] == 0) {
            for (int i = currentDiagonalIndex; i < systemLinearEquations.size; i++) {
                if (systemLinearEquations.leftMatrix[i][currentDiagonalIndex] != 0) {
                    swapRows(currentDiagonalIndex, i);
                }
            }
        }
    }

    private void swapRows(int a, int b) {
        double[] nonZeroRow = systemLinearEquations.leftMatrix[a];
        double nonZeroCellRight = systemLinearEquations.rightMatrix[a];
        double[] currentRow = systemLinearEquations.leftMatrix[b];
        double currentRowRight = systemLinearEquations.rightMatrix[b];

        systemLinearEquations.leftMatrix[a] = currentRow;
        systemLinearEquations.rightMatrix[a] = currentRowRight;

        systemLinearEquations.leftMatrix[b] = nonZeroRow;
        systemLinearEquations.rightMatrix[b] = nonZeroCellRight;
    }
}
