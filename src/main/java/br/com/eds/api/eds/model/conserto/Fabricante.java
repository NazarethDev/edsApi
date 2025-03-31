package br.com.eds.api.eds.model.conserto;


enum TipoProduto {
    Televisão,
    Notebook,
    Microondas,
    PC,
    Outro
}

public enum Fabricante {
    Samsung,
    Lg,
    Sony,
    Tcl,
    Hisense,
    Philco,
    Panasonic,
    Vizio,
    Xiaomi,
    Sharp,

    Apple,
    Acer,
    Dell,
    Lenovo,
    Hp,
    Asus,
    Multilaser,

    Brastemp,
    Electrolux,
    Consul,
    Bosch,
    Midea,


    Outro(TipoProduto.Televisão, TipoProduto.Notebook, TipoProduto.Microondas, TipoProduto.Outro, TipoProduto.PC);


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
