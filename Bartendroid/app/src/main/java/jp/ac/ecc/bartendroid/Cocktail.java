<<<<<<< HEAD
package jp.ac.ecc.bartendroid;

import java.util.ArrayList;

public class Cocktail {
    private String m_cocktail_name;
    private final ArrayList<Material> m_material_list;
    
    public Cocktail(String name) {
        super();
        m_material_list = new ArrayList<Material>();
        m_cocktail_name = name;
    }
    
    public void setCocktailName(String name) {
        m_cocktail_name = name;
    }
    
    public String getCocktailName() {
        return m_cocktail_name;
    }
    
    public void addMaterial(Material material) {
        m_material_list.add(material);
    }
    
    public ArrayList<Material> getMaterial() {
        return m_material_list;
    }
    
}
=======
package jp.ac.ecc.bartendroid;

import java.util.ArrayList;

public class Cocktail {
    private String m_cocktail_name;
    private final ArrayList<Material> m_material_list;
    
    public Cocktail(String name) {
        super();
        m_material_list = new ArrayList<Material>();
        m_cocktail_name = name;
    }
    
    public void setCocktailName(String name) {
        m_cocktail_name = name;
    }
    
    public String getCocktailName() {
        return m_cocktail_name;
    }
    
    public void addMaterial(Material material) {
        m_material_list.add(material);
    }
    
    public ArrayList<Material> getMaterial() {
        return m_material_list;
    }
    
}
>>>>>>> 684c3f6193e1357a65cc340a8ad0d5936f3ddc56
