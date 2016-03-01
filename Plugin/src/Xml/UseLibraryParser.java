package Xml;

import Analysis.Main.ProjectAnalysis;
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
    private ArrayList<Attribution> activity;
    private ArrayList<Attribution> error;
    private ArrayList<Attribution> event;



    public UseLibraryParser() {
        activity = new ArrayList<>();
        error = new ArrayList<>();
        event = new ArrayList<>();
        ProjectAnalysis projectAnalysis = ProjectAnalysis.getInstance(null, null);
        Filepath = projectAnalysis.makeAssetsPath("userLib.xml");
    }

    public void parse() {
        try {
            File ff = new File(Filepath);
            XmlPullParserFactory xppf = XmlPullParserFactory.newInstance();
            xppf.setNamespaceAware(true);
            XmlPullParser xpp = xppf.newPullParser();
            FileInputStream fis = new FileInputStream(ff);
            xpp.setInput(fis, null);

            int type = xpp.getEventType();
            while (type != XmlPullParser.END_DOCUMENT) {

                if (type == XmlPullParser.START_TAG) {
                    Attribution tempAttr = new Attribution();
                    for (int i = 0; i < xpp.getAttributeCount(); i++) {
                        tempAttr.setAttribute(xpp.getAttributeName(i));
                        tempAttr.setValue(xpp.getAttributeValue(i));
                    }
                    if (xpp.getName().equals("activity"))
                        activity.add(tempAttr);
                    else if (xpp.getName().equals("error"))
                        error.add(tempAttr);
                    else if (xpp.getName().equals("event"))
                        event.add(tempAttr);
                } else if (type == XmlPullParser.END_TAG) {

                }
                type = xpp.next();
            }
        } catch (Exception e2) {
            Messages.showInfoMessage("error1", "error1");

        }
    }

    public String getXmlName(String libraryFunction, int index) {
        if (libraryFunction.equals("activity"))
            return activity.get(index).getAttribute();
        else if (libraryFunction.equals("error"))
            return error.get(index).getAttribute();
        else if (libraryFunction.equals("event"))
            return event.get(index).getAttribute();
        else
            return null;
    }

    public String getComponentName(String libraryFunction, int index) {
        if (libraryFunction.equals("activity"))
            return activity.get(index).getValue();
        else if (libraryFunction.equals("error"))
            return error.get(index).getValue();
        else if (libraryFunction.equals("event"))
            return event.get(index).getValue();
        else
            return null;
    }

    private void makeXml(){

        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document doc = documentBuilder.newDocument();
            Element root = doc.createElement("resources");
            doc.appendChild(root);

            for (int i = 0; i < activity.size(); i++) {
                Element act = doc.createElement("activity");
                Attr attr;
                attr = doc.createAttribute(activity.get(i).getAttribute());
                attr.setValue(activity.get(i).getValue());
                act.setAttributeNode(attr);
                root.appendChild(act);
            }
            for (int i = 0; i < event.size(); i++) {
                Element eve = doc.createElement("event");
                Attr attr;
                attr = doc.createAttribute(event.get(i).getAttribute());
                attr.setValue(event.get(i).getValue());
                eve.setAttributeNode(attr);
                root.appendChild(eve);
            }
            for (int i = 0; i < error.size(); i++) {
                Element err = doc.createElement("error");
                Attr attr;
                attr = doc.createAttribute(error.get(i).getAttribute());
                attr.setValue(error.get(i).getValue());
                err.setAttributeNode(attr);
                root.appendChild(err);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(Filepath));
            transformer.transform(source, result);
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void append(String libraryFunction, String xmlName, String componentName) {
        Attribution tempAttr = new Attribution();
        tempAttr.setAttribute(xmlName);
        tempAttr.setValue(componentName);
        if (libraryFunction.equals("activity"))
            activity.add(tempAttr);
        else if (libraryFunction.equals("error"))
            error.add(tempAttr);
        else if (libraryFunction.equals("event"))
            event.add(tempAttr);
        makeXml();

    }
    //delete

    public void delete(String libraryFunction, String xmlName, String componentName) {
        if (libraryFunction.equals("activity")) {
            for (int i = 0; i < activity.size(); i++)
                if (activity.get(i).getAttribute().equals(xmlName)
                        && activity.get(i).getValue().equals(componentName))
                    activity.remove(i);
        } else if (libraryFunction.equals("error")) {
            for (int i = 0; i < error.size(); i++)
                if (error.get(i).getAttribute().equals(xmlName)
                        && error.get(i).getValue().equals(componentName))
                    error.remove(i);
        } else if (libraryFunction.equals("event")) {
            for (int i = 0; i < event.size(); i++)
                if (event.get(i).getAttribute().equals(xmlName)
                        && event.get(i).getValue().equals(componentName))
                    event.remove(i);
        }
        makeXml();

    }

    public void delete(String libraryFunction, int index) {
        if (libraryFunction.equals("activity"))
            activity.remove(index);
        else if (libraryFunction.equals("error"))
            error.remove(index);
        else if (libraryFunction.equals("event"))
            event.remove(index);

        makeXml();
}
    public int length(){
        return activity.size()+error.size()+event.size();
    }
    public int activityLength(){
        return activity.size();
    }

    public int eventLength(){
        return event.size();
    }
    public int errorLength(){
        return error.size();
    }
    }


