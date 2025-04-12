package br.com.eds.api.eds.service;

import br.com.eds.api.eds.model.domiciliar.Domiciliar;
import br.com.eds.api.eds.model.domiciliar.NovoEmDomicilio;
import br.com.eds.api.eds.repository.DomiciliarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DomiciliarService {

    @Autowired
    private DomiciliarRepository domiciliarRepository;

    @Transactional
    public Domiciliar saveDomicilar(NovoEmDomicilio novo){
        if (novo == null){
            return null;
        }
        Domiciliar domiciliar = new Domiciliar();
        domiciliar.setLogradouro(novo.logradouro());
        domiciliar.setNumeroCasa(novo.numeroCasa());
        domiciliar.setCep(novo.cep());
        domiciliar.setComplemento(novo.complemento());
        domiciliar.setPeriodo(novo.periodo());
        domiciliar.setData(novo.data());
        return domiciliarRepository.save(domiciliar);
    }
}