package jp.ac.ecc.bartendroid;

import java.util.ArrayList;

/**
 *
 * @author yulth
 */
public class MaterialArray {

    private Material material1;
    private Material material2;

    public MaterialArray(Material ma1, Material ma2) {
        material1 = ma1;
        material2 = ma2;
    }

    public boolean contain(Material material) {
        if (material1.getMaterialName().equals(material.getMaterialName())) {
            return true;
        }
        if (material2.getMaterialName().equals(material.getMaterialName())) {
            return true;
        }
        return false;
    }

    public boolean match(Material m1, Material m2) {
        if (m1.getMaterialName().equals(material1.getMaterialName())) {
            if (m2.getMaterialName().equals(material2.getMaterialName())) {
                return true;
            }
        }
        if (m1.getMaterialName().equals(material2.getMaterialName())) {
            if (m2.getMaterialName().equals(material1.getMaterialName())) {
                return true;
            }
        }
        return false;
    }
    
    public boolean matchList(ArrayList<Material> list) {
        return list.contains(material1) && list.contains(material2);
    }
}
