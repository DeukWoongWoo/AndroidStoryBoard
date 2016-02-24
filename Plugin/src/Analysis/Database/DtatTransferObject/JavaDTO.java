package Analysis.Database.DtatTransferObject;

import Analysis.Database.QueryBuilder.QueryBuilder;

import java.util.ArrayList;

/**
 * Created by woong on 2016-01-21.
 */
public class JavaDTO {
    private final String tableName = "Java";

    private int num;
    private String name;
    private String path;
    private String extendsValue;
    private String implementsValue;
    private String nextActivity;
    private String intentName;
    private String intentFuncName;

    private ArrayList<ComponentDTO> components;
    private ArrayList<XmlDTO> xmls;
    private ArrayList<EventDTO> events;

    private String[] columns;
    private int colCnt;

    private Object[] values;
    private int valCnt;

    public JavaDTO(){
        columns = new String[6];
        values = new Object[6];
    }

    public JavaDTO(int num, String name, String path, String extendsValue, String implementsValue, String nextActivity) {
        this.num = num;
        this.name = name;
        this.path = path;
        this.extendsValue = extendsValue;
        this.implementsValue = implementsValue;
        this.nextActivity = nextActivity;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setName(String name) {
        this.name = name;
        columns[colCnt++] = "name";
        values[valCnt++] = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
        columns[colCnt++] = "path";
        values[valCnt++] = path;
    }

    public void setExtendsValue(String extendsValue) {
        this.extendsValue = extendsValue;
        columns[colCnt++] = "extends";
        values[valCnt++] = extendsValue;
    }

    public void setImplementsValue(String implementsValue) {
        this.implementsValue = implementsValue;
        columns[colCnt++] = "implements";
        values[valCnt++] = implementsValue;
    }

    public void setNextActivity(String nextActivity) {
        this.nextActivity = nextActivity;
        columns[colCnt++] = "nextActivity";
        values[valCnt++] = nextActivity;
    }

    public void setIntentName(String intentName) {
        this.intentName = intentName;
    }

    public void setIntentFuncName(String intentFuncName) {
        this.intentFuncName = intentFuncName;
    }

    public void setComponents(ArrayList<ComponentDTO> components){
        this.components = components;
    }

    public void setComponent(ComponentDTO component){
        if(components == null) components = new ArrayList<>();
        components.add(component);
    }

    public void setXmls(ArrayList<XmlDTO> xmls){
        this.xmls = xmls;
    }

    public void setXml(XmlDTO xml){
        if(xmls == null) xmls = new ArrayList<>();
        xmls.add(xml);
    }

    public void setEvents(ArrayList<EventDTO> events){
        this.events = events;
    }

    public void setEvent(EventDTO event){
        if(events == null) events = new ArrayList<>();
        events.add(event);
    }

    public int getNum() {
        return num;
    }

    public String getName() {
        return name;
    }

    public String getExtendsValue() {
        return extendsValue;
    }

    public String getImplementsValue() {
        return implementsValue;
    }

    public String getNextActivity() {
        return nextActivity;
    }

    public String getIntentName() {
        return intentName;
    }

    public String getIntentFuncName() {
        return intentFuncName;
    }

    public ComponentDTO getComponent(int index){
        return components.get(index);
    }

    public XmlDTO getXml(int index) { return xmls.get(index); }

    public EventDTO getEvent(int index){
        return events.get(index);
    }

    public String getInsertQuery(){
        return QueryBuilder.insert().into(tableName).columns(columns).values(values).build();
    }

}
