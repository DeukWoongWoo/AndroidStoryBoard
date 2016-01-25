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
    public void addComponent(Component component,int index){
        components.add(index,component);
        size++;
    }
    public Component getComponent(int index){
        return components.get(index);
    }
    public void removeComponent(int index){
        components.remove(index);
    }
    public void removeComponent(String id){
        for(int i=0;i<components.size();i++){
            if(components.get(i).getId().equals(id))
                components.remove(i);
        }
    }
    public int size(){
        return size;
    }

}
