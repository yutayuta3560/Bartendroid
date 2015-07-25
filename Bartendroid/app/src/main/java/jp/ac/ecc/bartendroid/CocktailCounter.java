package jp.ac.ecc.bartendroid;

import java.util.ArrayList;
import java.util.List;

// カクテルを自動生成するクラス
public class CocktailCounter {

    // 現在所持している原材料のリスト
    private List<MaterialBring> materialBringList;
    private CocktailTaste tasteBase;
    private int tasteLevel;

    // コンストラクタ
    public CocktailCounter(CocktailTaste tastebase, int tastelevel) throws Exception {
        // tastelevel １〜３で指定する。
        if (tastelevel < 1 || tastelevel > 3) {
            throw new Exception();
        }
        this.materialBringList = new ArrayList<MaterialBring>();
        this.tasteBase = tastebase;
        this.tasteLevel = tastelevel;
    }

    // リストに追加させる
    public void addMaterial(MaterialBring material) {
        materialBringList.add(material);
    }

    // リストに追加させる
    public void addMaterial(ArrayList<MaterialBring> materialList) {
        materialBringList.addAll(materialList);
    }


    // この素材にはアルコールが含まれているかのフラグが必要

    // 必要な材料を指定する
    public ArrayList<MaterialBring> createNewCocktail(boolean isUseAlcohol) {

        // ベースになるカクテルの原材料を取得する
        // 原材料はtasteLavelに応じて選択する
        List<Material> basematerialList = getMaterialBaseForTasteLevel();
        

        return null;
    }

    private ArrayList<Material> getMaterialList() {
        ArrayList<Material> list = new ArrayList<Material>();
        for (MaterialBring material : materialBringList) {
            list.add(material.material);
        }
        return list;
    }

    private List<Material> getMaterialBaseForTasteLevel() {
        List<Material> materialList = getMaterialList();
        List<Material> targetMaterialList = new ArrayList<Material>();

        int level = tasteLevel + 2;
        for (Material material : materialList) {
            switch(tasteBase) {
                case SHIBUMI:
                    if (material.m_shibumi == level) {
                        targetMaterialList.add(material);
                    }
                    break;

                case SOUR:
                    if (material.m_sourness == level) {
                        targetMaterialList.add(material);
                    }
                    break;

                case CLEAR:
                    if (material.m_clear == level) {
                        targetMaterialList.add(material);
                    }
                    break;

                case BITTER:
                    if (material.m_bitterness == level) {
                        targetMaterialList.add(material);
                    }
                    break;

                case SWEET:
                    if (material.m_sweetness == level) {
                        targetMaterialList.add(material);
                    }
                    break;
            }
        }

        return targetMaterialList;
    }

}
