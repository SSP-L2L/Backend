package supplychain.web;

import java.util.HashMap;
import java.util.List;

import org.activiti.engine.impl.util.json.JSONArray;
import org.activiti.engine.impl.util.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
public class CoordController extends AbstractController {
	 @Autowired  
	 private RestTemplate restTemplate; 
	 
	@RequestMapping(value = "/coord/messages/{MsgName}", method = RequestMethod.POST , produces = "application/json")
	public ResponseEntity<HashMap<String, Object>> startProcessInstanceByMessage(@PathVariable("MsgName") String Msg_Name  , @RequestBody HashMap<String, Object> mp) throws InterruptedException {
		//流程引擎发送消息
		runtimeService.startProcessInstanceByMessage(Msg_Name , mp);

		return new ResponseEntity<HashMap<String, Object>>(mp,HttpStatus.OK);
	}
	
    @RequestMapping(value = "/getPaths" , method = RequestMethod.GET , produces = "application/json")
    public int hello(){
        String url = "http://restapi.amap.com/v3/direction/driving?origin=115.13506,30.21027&destination=115.5674,29.83692&output=json&key=ec15fc50687bd2782d7e45de6d08a023";
        String s = restTemplate.getForEntity(url, String.class).getBody();
        System.out.println(s);
      //  JSONObject json = new JSONObject(s); 
        JSONObject res = new JSONObject(s); 
	     JSONObject route =  (JSONObject) res.get("route");
		 @SuppressWarnings("unchecked")
		 JSONArray paths = (JSONArray) route.get("paths");
	     @SuppressWarnings("unchecked")
	     JSONObject path  = (JSONObject) paths.get(0);
	     int esti = Integer.parseInt((String) path.get("duration"));
	     return esti;
    }

}
