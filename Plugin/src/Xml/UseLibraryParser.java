package Xml;

import com.intellij.openapi.ui.Messages;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

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
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by cho on 2016-02-29.
 */
public class UseLibraryParser {
    private String Filepath;
    private ArrayList<Attribution>attributions;


    UseLibraryParser(String xmlPath){
        Filepath=xmlPath;
        attributions=new ArrayList<>();
    }
    public void parse(){
        try{
            File ff = new File(Filepath);
            XmlPullParserFactory xppf = XmlPullParserFactory.newInstance();
            xppf.setNamespaceAware(true);
            XmlPullParser xpp = xppf.newPullParser();
            FileInputStream fis = new FileInputStream(ff);
            xpp.setInput(fis,null);

            int type = xpp.getEventType();
            while(type != XmlPullParser.END_DOCUMENT){

                if(type == XmlPullParser.START_TAG) {
                    if(xpp.getName().equals("Item")){
                        for(int i=0;i<xpp.getAttributeCount();i++){
                            Attribution tempAttr=new Attribution();
                            tempAttr.setAttribute(xpp.getAttributeName(i));
                            tempAttr.setValue(xpp.getAttributeValue(i));
                            attributions.add(tempAttr);
                        }
                    }
                }
                else  if(type == XmlPullParser.END_TAG) {

                }
                type = xpp.next();
            }
        }catch(Exception e2){
            Messages.showInfoMessage("error1","error1");

        }
    }
    public String getXmlName(int index){
        return attributions.get(index).getAttribute();
    }
    public String getComponentName(int index){
        return attributions.get(index).getValue();
    }
    public void append(String xmlName,String componentName) {
        Attribution tempAttr = new Attribution();
        tempAttr.setAttribute(xmlName);
        tempAttr.setValue(componentName);
        attributions.add(tempAttr);
        Element element=null;

        try{
            DocumentBuilderFactory documentBuilderFactory= DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document doc= documentBuilder.newDocument();
            Element root = doc.createElement("resources");
            doc.appendChild(root);
            Element item = doc.createElement("Item");

            for(int i=0;i<attributions.size();i++){
                Attr attr;
                attr=doc.createAttribute(attributions.get(i).getAttribute());
                attr.setValue(attributions.get(i).getValue());
                item.setAttributeNode(attr);

            }
            root.appendChild(item);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(Filepath));
            transformer.transform(source,result);
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

    }
    //delete


}
