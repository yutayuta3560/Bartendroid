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
    public ArrayList<MaterialBring> createNewCocktail() {

        //

        return null;
    }

}
