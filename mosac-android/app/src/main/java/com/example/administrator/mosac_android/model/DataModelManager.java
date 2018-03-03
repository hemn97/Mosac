package com.example.administrator.mosac_android.model;

/**
 * Created by Administrator on 2018/3/1 0001.
 */

public class DataModelManager {
    // 像这样调用 DataModelManager.newInstance(PostModel.class.getName()).setParams(params).execute(callBack);
    public static BaseModel newInstance(String className){
        // 声明一个空的BaseModel
        BaseModel model = null;
        try {
            //利用反射机制获得对应Model对象的引用
            model = (BaseModel) Class.forName(className).newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return  model ;
    }
}
