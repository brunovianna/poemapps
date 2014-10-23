package net.brunovianna.poemapps;


public class Model{
 
    private int icon;
    private String title;
    private String counter;
 
 
    public Model(String title) {
        this(-1,title,null);
    }
    public Model(int icon, String title, String counter) {
        super();
        this.icon = icon;
        this.title = title;
        this.counter = counter;
    }
 
    public int getIcon() {
    	return this.icon;
    }
    
    public String getTitle() {
    	return this.title;
    }
    
}