package br.com.eds.api.eds.service.gerenciamento;

import br.com.eds.api.eds.model.gestao.est.consertoEst.DispositivoConsertoDTO;
import br.com.eds.api.eds.model.gestao.est.consertoEst.EstConsertoByCliente;
import br.com.eds.api.eds.model.gestao.est.impressaoEst.DimensaoEstDTO;
import br.com.eds.api.eds.model.gestao.est.impressaoEst.EstImpressaoAndDesignByCliente;
import br.com.eds.api.eds.model.gestao.est.impressaoEst.MaterialEstDTO;
import br.com.eds.api.eds.model.gestao.est.softwareEst.DispositivoSoftEstDTO;
import br.com.eds.api.eds.model.gestao.est.softwareEst.EstGeraisSoftEst;
import br.com.eds.api.eds.model.gestao.est.softwareEst.EstSoftwareByCliente;
import br.com.eds.api.eds.model.gestao.est.softwareEst.ServicoSoftEstatisticaDTO;
import br.com.eds.api.eds.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GestaoServiceEst {

    @Autowired
    ConsertoRepository consertoRepository;

    @Autowired
    CriacaoDesignRepository criacaoDesignRepository;

    @Autowired
    ImpressaoRepository impressaoRepository;

    @Autowired
    SoftwareRepository softwareRepository;

    @Autowired
    ClienteRepository clienteRepository;

    public final ResponseEntity verificaCliente(Long clienteId) {
        var cliente = clienteRepository.findById(clienteId).isEmpty();
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity impressaoPorCliente(Long clienteId){
        verificaCliente(clienteId);
        Integer totalImpressoes = impressaoRepository.contarPedidosPorCliente(clienteId);
        Double frquenciaImpressoes = impressaoRepository.calcularFrequenciaPedidos(clienteId);
        String dimensaoImpressaoMaisPedido = impressaoRepository.encontrarMaterialMaisPedido(clienteId);
        String materialMaisImpresso = impressaoRepository.encontrarDimensaoMaisPedida(clienteId);
        var response = new EstImpressaoAndDesignByCliente(totalImpressoes,frquenciaImpressoes,
                dimensaoImpressaoMaisPedido,materialMaisImpresso);

        return ResponseEntity.ok(response);
    }

    public ResponseEntity consertoPorCliente(Long id){
        verificaCliente(id);
        Integer totalSolicitacaoConserto = consertoRepository.contarSolicitacoesConserto(id);
        String tipoProdutoMaisConsertos = consertoRepository.encontrarTipoProdutoMaisConsertado(id);
        String fabricanteMaisConsertado = consertoRepository.encontrarFabricanteMaisRecorrente(id);
        Double frequenciaDeSolicitacoes = consertoRepository.calcularFrequenciaConsertos(id);

        var estatisticas = new EstConsertoByCliente(
                totalSolicitacaoConserto,tipoProdutoMaisConsertos,
                fabricanteMaisConsertado,frequenciaDeSolicitacoes);

        return ResponseEntity.ok(estatisticas);
    }

    public ResponseEntity softwarePorCliente(Long id){
        verificaCliente(id);
        String servicoMaisSolicitado = softwareRepository.encontrarServicoMaisSolicitado(id);
        Double frequenciaServicos = softwareRepository.calcularFrequenciaServicos(id);
        String tipoDispositivoMaisSolicitado = softwareRepository.encontrarTipoDispositivoMaisSolicitado(id);

        var estatisticas = new EstSoftwareByCliente(servicoMaisSolicitado,
                frequenciaServicos,tipoDispositivoMaisSolicitado);

        return ResponseEntity.ok(estatisticas);
    }

    public ResponseEntity CriacaoDesignPorCliente(Long clienteId){
        verificaCliente(clienteId);
        Integer totalImpressoes = criacaoDesignRepository.contarPedidosPorCliente(clienteId);
        Double frquenciaImpressoes = criacaoDesignRepository.calcularFrequenciaPedidos(clienteId);
        String dimensaoImpressaoMaisPedido = criacaoDesignRepository.encontrarMaterialMaisPedido(clienteId);
        String materialMaisImpresso = criacaoDesignRepository.encontrarDimensaoMaisPedida(clienteId);
        var resposta = new EstImpressaoAndDesignByCliente(totalImpressoes,frquenciaImpressoes,
                dimensaoImpressaoMaisPedido,materialMaisImpresso);

        return ResponseEntity.ok(resposta);
    }

    public ResponseEntity estatisticasGeraisPorMesSoftware(int mes, int ano) {
        List<Object[]> servicos = softwareRepository.listarServicosMaisSolicitados(mes, ano);
        List<Object[]> dispositivos = softwareRepository.listarDispositivosMaisSolicitados(mes, ano);
        List<ServicoSoftEstatisticaDTO> servicoDTOs = servicos.stream()
                .map(obj -> new ServicoSoftEstatisticaDTO((String) obj[0], ((Number) obj[1]).intValue()))
                .toList();

        List<DispositivoSoftEstDTO> dispositivoDTOs = dispositivos.stream()
                .map(obj -> new DispositivoSoftEstDTO((String) obj[0], ((Number) obj[1]).intValue()))
                .toList();

        var estatisticas = new EstGeraisSoftEst(servicoDTOs, dispositivoDTOs);

        return ResponseEntity.ok(estatisticas);
    }

    public ResponseEntity estatisticasMateriaisPorMes(int mes, int ano) {
        List<Object[]> materiais = impressaoRepository.contarMateriaisPorMes(mes, ano);

        List<MaterialEstDTO> materialDTOs = materiais.stream()
                .map(obj -> new MaterialEstDTO((String) obj[0], ((Number) obj[1]).intValue()))
                .toList();

        return ResponseEntity.ok(materialDTOs);
    }

    public ResponseEntity<?> estatisticasDimensoesPorMes(int mes, int ano) {
        List<Object[]> dimensoes = impressaoRepository.contarDimensoesPorMes(mes, ano);

        List<DimensaoEstDTO> dimensaoDTOs = dimensoes.stream()
                .map(obj -> new DimensaoEstDTO((String) obj[0], ((Number) obj[1]).intValue()))
                .toList();

        return ResponseEntity.ok(dimensaoDTOs);
    }

    public ResponseEntity<?> dispositivosMaisConsertadosPorMes(int mes, int ano) {
        List<Object[]> dispositivos = consertoRepository.contarDispositivosPorMes(mes, ano);

        List<DispositivoConsertoDTO> dispositivosDTO = dispositivos.stream()
                .map(obj -> new DispositivoConsertoDTO((String) obj[0], ((Number) obj[1]).intValue()))
                .toList();

        return ResponseEntity.ok(dispositivosDTO);
    }



}
