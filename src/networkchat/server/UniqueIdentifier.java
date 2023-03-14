/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package networkchat.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author FreezinFire
 */
public class UniqueIdentifier {
    private static List<Integer> ids = new ArrayList<Integer>();
    private static final int RANGE = 10000;
    
    private static int index = 0;
    
    static {
        for (int i=0; i<RANGE; i++){
            ids.add(i);
        }
        Collections.shuffle(ids);
    }
    
    private UniqueIdentifier(){   
    }
    
    public static int getIdentifier(){
        if(index > ids.size() - 1) index = 0;
        return ids.get(index++);
    }
}
