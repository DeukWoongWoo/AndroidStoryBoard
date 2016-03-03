package Xml;


import Analysis.Main.ProjectAnalysis;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

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

/**
 * Created by cho on 2016-03-03.
 */
public class makeXml {

    public makeXml(){

    }

    public static void makeUserLib(String assetPath) {
        try {
            File f = new File(assetPath);
            if(f.isFile()){
                DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
                Document doc = documentBuilder.newDocument();
                Element resources =  doc.createElement("resource");
                doc.appendChild(resources);

                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(new File(assetPath));
                transformer.transform(source,result);
            }
        }catch (Exception e){

        }
    }
    public static void makeUserId(String userIdXmlpath){
        try{
            File f = new File(userIdXmlpath);
            if(f.isFile()) {
                DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
                Document doc = documentBuilder.newDocument();
                Element resources =  doc.createElement("resource");
                doc.appendChild(resources);
                Element userId = doc.createElement("userId");
                resources.appendChild(userId);
                Element appName = doc.createElement("appName");
                resources.appendChild(appName);

                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(new File(userIdXmlpath));
                transformer.transform(source,result);
            }
        }catch (Exception e){

        }
    }
    public static void makeActivityxml(String xmlName) {
        try{

            ProjectAnalysis projectAnalysis = ProjectAnalysis.getInstance(null);
            String xmlPath = projectAnalysis.makeResourcePath(xmlName)+".xml";

            DocumentBuilderFactory documentBuilderFactory= DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document doc= documentBuilder.newDocument();
            Element currentlayout=null;
            doc.setDocumentURI("android");

            currentlayout = doc.createElement("RelativeLayout");
            Attr attr;
            attr=doc.createAttributeNS("http://schemas.android.com/apk/res/android","layout_width");
            attr.setValue("match_parent");
            attr.setPrefix("android");
            currentlayout.setAttributeNode(attr);
            attr=doc.createAttributeNS("http://schemas.android.com/apk/res/android","layout_height");
            attr.setValue("match_parent");
            attr.setPrefix("android");
            currentlayout.setAttributeNode(attr);
            doc.appendChild(currentlayout);

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
