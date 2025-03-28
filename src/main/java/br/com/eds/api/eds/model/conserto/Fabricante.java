package br.com.eds.api.eds.model.conserto;


enum TipoProduto {
    TELEVISOR,
    NOTEBOOK,
    MICROONDAS,
    OUTRO
}

public enum Fabricante {
    SAMSUNG(TipoProduto.TELEVISOR, TipoProduto.NOTEBOOK, TipoProduto.MICROONDAS),
    LG(TipoProduto.TELEVISOR, TipoProduto.MICROONDAS),
    SONY(TipoProduto.TELEVISOR),
    TCL(TipoProduto.TELEVISOR),
    HISENSE(TipoProduto.TELEVISOR),
    PHILCO(TipoProduto.TELEVISOR, TipoProduto.MICROONDAS),
    PANASONIC(TipoProduto.TELEVISOR, TipoProduto.MICROONDAS),
    VIZIO(TipoProduto.TELEVISOR),
    XIAOMI(TipoProduto.TELEVISOR),
    SHARP(TipoProduto.TELEVISOR),

    APPLE(TipoProduto.NOTEBOOK),
    ACER(TipoProduto.NOTEBOOK),
    DELL(TipoProduto.NOTEBOOK),
    LENOVO(TipoProduto.NOTEBOOK),
    HP(TipoProduto.NOTEBOOK),
    ASUS(TipoProduto.NOTEBOOK),
    MULTILASER(TipoProduto.NOTEBOOK),

    BRASTEMP(TipoProduto.MICROONDAS),
    ELECTROLUX(TipoProduto.MICROONDAS),
    CONSUL(TipoProduto.MICROONDAS),
    BOSCH(TipoProduto.MICROONDAS),
    MIDEA(TipoProduto.MICROONDAS),

    OUTRO(TipoProduto.TELEVISOR, TipoProduto.NOTEBOOK, TipoProduto.MICROONDAS, TipoProduto.OUTRO);


    private TipoProduto [] categorias;

    public TipoProduto [] getCategorias(){
        return categorias;
    }

    public  boolean pertenceCategoria(TipoProduto tipo){
        for (TipoProduto tipoProduto : categorias){
            if (tipoProduto == tipo){
                return true;
            }
        }
        return false;
    }

    Fabricante(TipoProduto... categorias) {
        this.categorias = categorias;
    }
}
