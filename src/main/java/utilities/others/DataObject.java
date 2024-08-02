/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package utilities.others;

import java.util.HashMap;
import java.util.Map;

public interface DataObject<T> {
    static Map<String, Object> d = new HashMap<>();

    public static Object get(String key) {
        return d.get(key);
    }

    public T get();

    public void set(T item); //    {
}
