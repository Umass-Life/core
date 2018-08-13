package api.fitbit_account.fitbit_subscription;

import api.fitbit_account.fitbit_auth.FitbitAuthenticationService;
import api.fitbit_account.fitbit_user.FitbitUser;
import api.fitbit_account.fitbit_user.FitbitUserService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import util.ColorLogger;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@Controller
@RequestMapping(value = "/fitbit")
public class SubscriptionController {
    private final static String verificationCode = "d8afed4cd5d690d9224fd3a403d10166ac59e82a7e270223cbdcce0307d6a970";
    private Logger logger = Logger.getLogger(SubscriptionController.class.getSimpleName());
    private ColorLogger colorLogger = new ColorLogger(logger);

    @Autowired
    private FitbitAuthenticationService authenticationService;

    @Autowired
    private FitbitUserService fitbitUserService;

    @Autowired
    private FitbitSubscriptionService subscriptionService;

    @RequestMapping(value="/webhook", method = RequestMethod.GET)
    public ResponseEntity webhook(@RequestParam(value="verify") String verify){
        try {
            colorLogger.info("VERI CODE: " +  verify);
            if (verify!=null && verify.length() > 0){
                if(verify.equals(verificationCode)){
                    colorLogger.info("correct code");
                    return ResponseEntity.status(204).body("");
                }
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        colorLogger.info("incorrect code");
        return ResponseEntity.status(404).body("");
    }

    @RequestMapping(value="/webhook", method = RequestMethod.POST)
    public ResponseEntity subscription(@RequestBody Map<String, Object> body){
        try {
            colorLogger.info(body.toString());
            return ResponseEntity.status(204).body("");
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(404).body("");
        }
    }
    // https://api.fitbit.com/1/user/-/[collection-path]/apiSubscriptions/[subscription-id].json
    @RequestMapping(value="/subscribe", method = RequestMethod.GET)
    public ResponseEntity subscribe(@RequestParam(value="fid",required=false) String fid,
                                    @RequestParam(value="id",required=false) Long id){
        Map<String, Object> map = new HashMap<>();
        try {
            FitbitUser fitbitUser = getFitbitUser(fid, id);
            JsonNode node = subscriptionService.subscribeUser(fitbitUser);
            map.put("payload", node);
            return ResponseEntity.ok(map);
        } catch (Exception e){
            e.printStackTrace();
            colorLogger.severe(e.getMessage());
            map = new HashMap<>();
            map.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(map);
        }
    }

    private FitbitUser getFitbitUser(String fitbitId, Long userId) throws IllegalArgumentException{
        FitbitUser fitbitUser = null;
        if (userId!=null){
            fitbitUser = fitbitUserService.getById(userId).orElseThrow(
                    () -> new IllegalArgumentException("Unable to find FitbitUser id = " + userId)
            );
        } else if (fitbitId != null){
            fitbitUser = fitbitUserService.getByFitbitId(fitbitId).orElseThrow(
                    () -> new IllegalArgumentException("UNable to find FitbitUser fitbitId = " + fitbitId)
            );
        }

        if (fitbitUser == null){
            throw new IllegalArgumentException("No Fitbit user information provided");
        }
        return fitbitUser;
    }

}