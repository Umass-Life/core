package api.ema;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import util.ColorLogger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Controller
@RequestMapping("/ema")
public class EMAController {
    private static final Logger logger = Logger.getLogger(EMAController.class.getSimpleName());
    private static final ColorLogger colorLog = new ColorLogger(logger);
    @Autowired
    private EMAService emaService;
    @Autowired
    private StressLevelService stressLevelService;

    @RequestMapping(value = {"/", ""}, method = RequestMethod.GET)
    public ResponseEntity<Map> list(){
        Map<String, Object> responseJson = new HashMap<>();
        responseJson.put(StressLevel.PLURAL, emaService.list());
        return ResponseEntity.ok(responseJson);
    }

    @RequestMapping(value = {"/",""}, method = RequestMethod.POST)
    public ResponseEntity<Map> post(@RequestBody JsonNode node){
        Map<String, Object> responseJson = new HashMap<>();
        try{
            colorLog.info(node);
            List<EMA> emas = emaService.create(node);
            responseJson.put("success", "true");
            colorLog.info("SAVED: \n%s", emas);
            return ResponseEntity.ok(responseJson);
        } catch(Exception e){
//            e.printStackTrace();
            colorLog.severe(e.getMessage());
            responseJson.put("success", "false");
            return ResponseEntity.ok(responseJson);
        }
    }

    @RequestMapping(value = "/stress", method = RequestMethod.GET)
    public ResponseEntity<Map> stress(){
        System.out.println("stress");
        Map<String, Object> responseJson = new HashMap<>();
        responseJson.put(StressLevel.PLURAL, stressLevelService.list());
        return ResponseEntity.ok(responseJson);
    }



    @RequestMapping(value = {"/test"}, method = RequestMethod.GET)
    public ResponseEntity test(){
        Map<String, String> jsonMap = new HashMap<>();
        jsonMap.put("hello", "from server");
        System.out.println("Hello from fitbit");
        return ResponseEntity.ok(jsonMap);
    }
}

/*

{
  "msgtype": "batchslice",
  "id": "1534219271888",
  "type": "stress_level",
  "data": [
    {
      "timestamp": 1534219234,
      "level": "Neither",
      "strainId": 1,
      "fitbitId": "M9GYG1"
    }
  ],
  "slice": 0,
  "slices": 1,
  "type_count": [
    2,
    3
  ]
}



* */
