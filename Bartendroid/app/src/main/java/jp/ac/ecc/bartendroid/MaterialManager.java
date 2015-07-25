package cocktailmaker;

import java.util.ArrayList;

public class MaterialManager {
    
    private ArrayList<Cocktail> m_CocktailList;
    private ArrayList<Material> m_MaterialList;
    
    public MaterialManager() {
        m_CocktailList = new ArrayList<Cocktail>();
        m_MaterialList = new ArrayList<Material>();
    }
    
    public void addCocktail(Cocktail cock) {
        m_CocktailList.add(cock);
    }
    
    public void addCocktails(ArrayList<Cocktail> cock) {
        m_CocktailList.addAll(cock);
    }
    
    public void addMaterial(Material material) {
        m_MaterialList.add(material);
    }
    
    public void addMaterials(ArrayList<Material> material) {
        m_MaterialList.addAll(material);
    }
    
    public ArrayList<Cocktail> getExistCocktail(ArrayList<Material> materialList, int remainMaterial, boolean onlyRemain) {
        
        ArrayList<Cocktail> cocktailList = new ArrayList<Cocktail>();
        
        // カクテルをひとつひとつ取り出す
        for (Cocktail cocktail : m_CocktailList) {
            // あと何個、素材が足りないかを確認
            int shortage = 0;
            for (Material material : cocktail.getMaterial()) {
                if (!materialList.contains(material)) {
                    shortage++;
                }
            }
            if (onlyRemain) {
                if (shortage == remainMaterial) {
                    cocktailList.add(cocktail);
                }
            } else {
                if (shortage <= remainMaterial) {
                    cocktailList.add(cocktail);
                }
            }
            
        }
        
        return cocktailList;
    }
    
    public Cocktail makeNewCocktail(ArrayList<Material> materialList, CocktailTaste taste) {
        
        return new Cocktail("おいしいカクテル");
    }
}
