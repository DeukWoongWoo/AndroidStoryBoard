package Xml;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Created by cho on 2016-02-25.
 */
public class XmlToJsonObject {
    JSONObject webJson;
    JSONObject pluginJson;

    public XmlToJsonObject(){

    }
    public XmlToJsonObject(JSONObject webJson, JSONObject pluginJson){
        this.webJson = webJson;
        this.pluginJson=pluginJson;
    }
    public void setWebJson(JSONObject webJson){
        this.webJson = webJson;
    }
    public void setPluginJson(JSONObject pluginJson){
        this.pluginJson =pluginJson;
    }
    public JSONObject getWebJson(){
        return this.webJson;
    }
    public JSONObject getPluginJson(){
        return this.pluginJson;
    }
}
