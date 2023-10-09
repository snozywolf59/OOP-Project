/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MainPackage;

/**
 * Một lớp để gọi đến 1 instance của ViewFactory không cần khai báo.
 * Đồng thời instance này được đồng bộ.
 * @author LENOVO
 */
public final class Model {
    private static Model model;
    private final ViewFactory viewFactory;
    
    private Model() {
        this.viewFactory = new ViewFactory();
    }
    
    public static synchronized Model getInstance() {
        if (model == null) {
            model = new Model();
        }
        return model;
    }
    
    public ViewFactory getViewFactory() {
        return viewFactory;
    }
}
