package AI_testing_TestData;

import weka.core.Instance;
import weka.core.DenseInstance;
import weka.classifiers.functions.SMO;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class SyntheticDataGenerator {
    public static void main(String[] args) throws Exception {
        // Load the model
        SMO classifier = new SMO();
        DataSource source = new DataSource("path/to/dataset.arff");
        Instances data = source.getDataSet();
        if (data.classIndex() == -1)
            data.setClassIndex(data.numAttributes() - 1);
        classifier.buildClassifier(data);

        // Generate synthetic data
        for (int i = 0; i < 10; i++) {  // Generate 10 synthetic instances
            Instance instance = new DenseInstance(data.numAttributes());
            instance.setDataset(data);
            for (int j = 0; j < data.numAttributes() - 1; j++) {
                instance.setValue(j, Math.random()); // Replace with appropriate value generation
            }
            double label = classifier.classifyInstance(instance);
            instance.setClassValue(label);
            System.out.println(instance);
        }
    }
}

