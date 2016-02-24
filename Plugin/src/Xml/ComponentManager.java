package Xml;

import java.util.ArrayList;

/**
 * Created by cho on 2016-01-24.
 */
public class ComponentManager {

    private ArrayList <Component> components;
    private int size;
    ComponentManager(){
        components=new ArrayList<Component>();
        size=0;
}
    public void addComponent(Component component){
        components.add(component);
        size++;
    }

    public Component getComponent(int index){
        return components.get(index);
    }

    public int size(){
        return size;
    }

}
