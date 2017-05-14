package tk.burdukowsky.BayesClassifierWeb;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.Stream;


/**
 * Создано пользователем STANISLAV 13.05.2017 16:42.
 */
@Controller
public class MainController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/task", method = RequestMethod.GET)
    public String task(@RequestParam(value = "classes") String classesStr,
                       @RequestParam(value = "propertiesCount") int propertiesCount,
                       @RequestParam(value = "trainingSampleSize") int trainingSampleSize,
                       Model model) {
        int[] classes = Stream.of(classesStr.split(",")).mapToInt(Integer::parseInt).toArray();

        int[][] trainingSample = new int[trainingSampleSize][];
        int[] answers = new int[trainingSampleSize];
        int[] allowedPropertiesValues = new int[]{0, 1};
        for (int i = 0; i < trainingSampleSize; i++) {
            trainingSample[i] = new int[propertiesCount];
            for (int j = 0; j < propertiesCount; j++) {
                trainingSample[i][j] = getRandomFromArray(allowedPropertiesValues);
            }
            answers[i] = getRandomFromArray(classes);
        }

        int[] test = new int[propertiesCount];
        for (int i = 0; i < propertiesCount; i++) {
            test[i] = getRandomFromArray(allowedPropertiesValues);
        }

        model.addAttribute("propertiesCount", propertiesCount);
        model.addAttribute("trainingSampleSize", trainingSampleSize);
        model.addAttribute("trainingSample", trainingSample);
        model.addAttribute("answers", answers);
        model.addAttribute("test", test);
        return "task";
    }

    private int getRandomFromArray(int[] array) {
        int rnd = new Random().nextInt(array.length);
        return array[rnd];
    }

    @RequestMapping(value = "/concrete-example", method = RequestMethod.GET)
    public String concreteExample(Model model) {
        final int TRAINING_SAMPLE_SIZE = 31;

        int[][] trainingSample = new int[TRAINING_SAMPLE_SIZE][];
        trainingSample[0] = new int[]{0, 0, 0, 0, 1, 0, 0, 1, 1, 0, 0, 0};
        trainingSample[1] = new int[]{1, 1, 0, 0, 0, 0, 1, 1, 1, 0, 1, 0};
        trainingSample[2] = new int[]{1, 1, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0};
        trainingSample[3] = new int[]{1, 0, 0, 1, 0, 0, 1, 1, 0, 0, 0, 0};
        trainingSample[4] = new int[]{0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0};
        trainingSample[5] = new int[]{0, 1, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1};
        trainingSample[6] = new int[]{1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0};
        trainingSample[7] = new int[]{1, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1};
        trainingSample[8] = new int[]{0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0};
        trainingSample[9] = new int[]{0, 1, 1, 0, 1, 0, 1, 1, 0, 1, 0, 0};
        trainingSample[10] = new int[]{1, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0};
        trainingSample[11] = new int[]{0, 1, 0, 0, 1, 1, 1, 1, 0, 0, 1, 0};
        trainingSample[12] = new int[]{1, 1, 0, 0, 1, 1, 1, 1, 0, 0, 1, 0};
        trainingSample[13] = new int[]{1, 1, 0, 0, 1, 0, 1, 1, 0, 0, 1, 0};
        trainingSample[14] = new int[]{1, 1, 1, 0, 1, 0, 0, 1, 0, 0, 0, 0};
        trainingSample[15] = new int[]{0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0};
        trainingSample[16] = new int[]{0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0};
        trainingSample[17] = new int[]{0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0};
        trainingSample[18] = new int[]{1, 0, 1, 1, 0, 0, 1, 0, 1, 0, 0, 0};
        trainingSample[19] = new int[]{1, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0};
        trainingSample[20] = new int[]{1, 0, 0, 1, 0, 0, 1, 0, 1, 0, 1, 0};
        trainingSample[21] = new int[]{0, 0, 1, 0, 1, 0, 0, 1, 1, 0, 0, 1};
        trainingSample[22] = new int[]{0, 0, 0, 1, 0, 1, 0, 1, 1, 0, 1, 0};
        trainingSample[23] = new int[]{1, 1, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0};
        trainingSample[24] = new int[]{1, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 0};
        trainingSample[25] = new int[]{1, 1, 0, 0, 1, 1, 0, 0, 1, 0, 0, 1};
        trainingSample[26] = new int[]{1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 1};
        trainingSample[27] = new int[]{1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1};
        trainingSample[28] = new int[]{1, 1, 1, 1, 0, 0, 1, 1, 1, 0, 0, 0};
        trainingSample[29] = new int[]{1, 1, 0, 1, 1, 0, 0, 0, 0, 1, 0, 0};
        trainingSample[30] = new int[]{0, 0, 1, 1, 1, 1, 0, 0, 0, 1, 0, 1};

        int[] answers = new int[TRAINING_SAMPLE_SIZE];
        answers[0] = 1;
        answers[1] = 1;
        answers[2] = 1;
        answers[3] = 1;
        answers[4] = 1;
        answers[5] = 1;
        answers[6] = 1;
        answers[7] = 1;
        answers[8] = 1;
        answers[9] = 1;
        answers[10] = 1;
        answers[11] = 1;
        answers[12] = 1;
        answers[13] = 1;
        answers[14] = 1;
        answers[15] = 1;
        answers[16] = 1;
        answers[17] = 2;
        answers[18] = 2;
        answers[19] = 2;
        answers[20] = 2;
        answers[21] = 2;
        answers[22] = 2;
        answers[23] = 2;
        answers[24] = 2;
        answers[25] = 2;
        answers[26] = 2;
        answers[27] = 2;
        answers[28] = 2;
        answers[29] = 2;
        answers[30] = 2;

        int[] test = new int[]{0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 0};

        model.addAttribute("propertiesCount", 12);
        model.addAttribute("trainingSampleSize", TRAINING_SAMPLE_SIZE);
        model.addAttribute("trainingSample", trainingSample);
        model.addAttribute("answers", answers);
        model.addAttribute("test", test);
        return "task";
    }

    @RequestMapping(value = "/work", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> work(@RequestParam(value = "trainingSampleJson") String trainingSampleJson,
                                    @RequestParam(value = "answersJson") String answersJson,
                                    @RequestParam(value = "testJson") String testJson) {
        Map<String, Object> result = new HashMap<>();
        try {
            ObjectMapper om = new ObjectMapper();
            int[][] trainingSample = om.readValue(trainingSampleJson, int[][].class);
            int[] answers = om.readValue(answersJson, int[].class);
            int[] test = om.readValue(testJson, int[].class);
            Classifier classifier = new Classifier(trainingSample, answers);
            int testClass = classifier.getClazz(test);
            result.put("status", "ok");
            result.put("testClass", testClass);
        } catch (IOException e) {
            e.printStackTrace();
            result.put("status", "error");
        }
        return result;
    }
}
