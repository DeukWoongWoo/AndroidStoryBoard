package Xml;

import Analysis.Main.ProjectAnalysis;
import com.intellij.icons.AllIcons;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by cho on 2016-02-25.
 */
public class JsonToXml {

    public JsonToXml(){

    }
    public void make(String jsonPath){
        ArrayList<Component> componentArrayList=new ArrayList<Component>();
        JsonToComponent(jsonPath,componentArrayList);
    }

    private void JsonToAttribute(JSONObject jsonObject, Component component){

        Iterator iterator = jsonObject.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry entry = (Map.Entry) iterator.next();
            String attr = (String) entry.getKey();
            String value = (String) entry.getValue();
            component.setAttributes(attr,value);
        }
        component.setAttributeCount();
    }
    private void JsonToObject(JSONArray jsonArray, ArrayList <Component> componentArrayList){
        JSONObject jsonObject;
        JSONArray jsonLayout = null;
        JSONObject jsonAttr;
        String tagName = null;
        Component component;
        for(int i = 0; i<jsonArray.size();i++){
            component = new Component();
            jsonObject = (JSONObject) jsonArray.get(i);
            Iterator iterator = jsonObject.entrySet().iterator();
            while(iterator.hasNext()){
                Map.Entry entry = (Map.Entry) iterator.next();
                String key = (String) entry.getKey();

                if(key.equals("type")){
                    tagName=(String) entry.getValue();
                    component.tagName=tagName;
                }
                else if(key.equals("attribute")){
                    jsonAttr = (JSONObject) entry.getValue();
                    JsonToAttribute(jsonAttr,component);
                }
                else if(key.equals("object")){//오브젝트있으면 레이아웃이다.
                    jsonLayout= (JSONArray) entry.getValue();
                }else if(key.equals("Library")){
                    String assetPath ="C:/Users/cho/Desktop/TestActivity/app/src/main/assets/userLib.xml";
                    UseLibraryParser useLibraryParser = new UseLibraryParser(assetPath);
                    useLibraryParser.parse();
                    if(entry.getValue().equals("event"))
                        component.library="event";
                    else if(entry.getValue().equals("error"))
                        component.library="error";
                }
            }
            componentArrayList.add(component);
            if(tagName.equals("RelativeLayout")){
                JsonToObject(jsonLayout,componentArrayList);
                //EndTag넣을곳
                Component component1 = new Component();
                component1.tagName="EndTag";
                componentArrayList.add(component1);
            }
        }
    }
    private void JsonToActivity(JSONArray jsonArray,ArrayList <Component> componentArrayList){
        JSONObject jsonObject;
        String xmlName = null;
        String xmlPath=null;
        for(int i =0;i<jsonArray.size();i++){
            componentArrayList = new ArrayList<Component>();
            jsonObject= (JSONObject) jsonArray.get(i);
            Iterator iterator = jsonObject.entrySet().iterator();
            while(iterator.hasNext()){
                Map.Entry entry = (Map.Entry) iterator.next();
                String key = (String) entry.getKey();
                if(key.equals("name")){
                    //// TODO: 2016-02-24 xmlName은 여기서 받는다
                    ProjectAnalysis projectAnalysis = ProjectAnalysis.getInstance(null, null);
                    xmlName= (String) entry.getValue();
                    xmlPath = projectAnalysis.makeResourcePath(xmlName);
                }
                else if(key.equals("object")){
                    JSONArray jsonObjectArray = (JSONArray) entry.getValue();
                    JsonToObject(jsonObjectArray,componentArrayList);
                }else if(key.equals("Library")){
                    String assetPath ="C:/Users/cho/Desktop/TestActivity/app/src/main/assets/userLib.xml";
                    UseLibraryParser useLibraryParser = new UseLibraryParser(assetPath);
                    useLibraryParser.parse();
                    useLibraryParser.append("activity",xmlName,xmlName);
                }
            }

            for(int j=0;j<componentArrayList.size();j++){
                Component component = componentArrayList.get(j);
                if(!component.library.equals("null")){
                    String assetPath ="C:/Users/cho/Desktop/TestActivity/app/src/main/assets/userLib.xml";
                    UseLibraryParser useLibraryParser = new UseLibraryParser(assetPath);
                    useLibraryParser.parse();
                    useLibraryParser.append(component.library,xmlName,makeId(component.getId()));
                }
            }
            //여기가 다른 액티비티로넘어가는 곳(xml을 만들자)
            appendXmlCode(componentArrayList,xmlPath);
        }
    }
    private String makeId(String id){
        char[] charId = id.toCharArray();
        String makeId=new String(charId,5,id.length()-5);
        return makeId;

    }
    private void JsonToComponent(String filePath,ArrayList <Component> componentArrayList){
        try {
            FileReader reader = new FileReader(filePath);
            JSONParser jsonParser = new JSONParser();
            JSONArray jsonLayout = new JSONArray();
            JSONObject jsonObject =(JSONObject) jsonParser.parse(reader);
            Iterator iterator = jsonObject.entrySet().iterator();
            String key;
            String value;
            while(iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                if(entry.getKey().equals("activity")){
                    jsonLayout = (JSONArray) entry.getValue();
                    JsonToActivity(jsonLayout,componentArrayList);
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int xmlTop=0;
    private Element[] xmlStack= new Element[255];
    private void xmlPush(Element element){
        xmlStack[xmlTop++]=element;
    }
    private Element xmlPop(){
        return xmlStack[--xmlTop];
    }
    private Element currentXmlStackData(){
        return xmlStack[xmlTop-1];
    }
    private void appendXmlCode(ArrayList<Component> componentArrayList, String xmlPath){
        try{
            DocumentBuilderFactory documentBuilderFactory= DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document doc= documentBuilder.newDocument();
            Element currentlayout=null;
            doc.setDocumentURI("android");

            for(int i=0;i<componentArrayList.size();i++){
                Attr attr;
                Component component = componentArrayList.get(i);
                if(component.getTagName().equals("RelativeLayout")){
                    currentlayout = doc.createElement(component.getTagName());
                    for(int j=0;j<component.getAttributeCount();j++){
                        attr=doc.createAttributeNS("http://schemas.android.com/apk/res/android",component.getAttributes(j));
                        attr.setValue(component.getAttributesValue(j));
                        attr.setPrefix("android");
                        currentlayout.setAttributeNode(attr);
                    }
                    if(xmlTop == 0) {//root
                        doc.appendChild(currentlayout);
                    }
                    else{
                        Element element = currentXmlStackData();
                        element.appendChild(currentlayout);
                    }
                    xmlPush(currentlayout);
                }else if(component.getTagName().equals("EndTag")){//endTag
                    xmlPop();
                }
                else{//component
                    Element element = doc.createElement(component.getTagName());
                    Element parentElement = currentXmlStackData();
                    for(int j=0;j<component.getAttributeCount();j++){
                        attr=doc.createAttributeNS("http://schemas.android.com/apk/res/android",component.getAttributes(j));
                        attr.setValue(component.getAttributesValue(j));
                        attr.setPrefix("android");
                        element.setAttributeNode(attr);
                    }
                    parentElement.appendChild(element);
                }

            }



            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(xmlPath));
            transformer.transform(source,result);

        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

    }

}
