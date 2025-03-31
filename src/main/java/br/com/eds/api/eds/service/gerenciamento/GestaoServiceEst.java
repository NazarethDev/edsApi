package br.com.eds.api.eds.service.gerenciamento;

import br.com.eds.api.eds.model.gestao.est.consertoEst.DispositivoConsertoDTO;
import br.com.eds.api.eds.model.gestao.est.consertoEst.EstConsertoByCliente;
import br.com.eds.api.eds.model.gestao.est.impressaoEst.DimensaoEstDTO;
import br.com.eds.api.eds.model.gestao.est.impressaoEst.EstImpressaoAndDesignByCliente;
import br.com.eds.api.eds.model.gestao.est.impressaoEst.MaterialEstDTO;
import br.com.eds.api.eds.model.gestao.est.softwareEst.FrequenciaServicoResponse;
import br.com.eds.api.eds.model.software.Software;
import br.com.eds.api.eds.model.software.TipoServicoSoftware;
import br.com.eds.api.eds.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        String dimensaoImpressaoMaisPedido = impressaoRepository.encontrarDimensaoMaisPedida(clienteId);
        String materialMaisImpresso = impressaoRepository.encontrarMaterialMaisPedido(clienteId);
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

    public ResponseEntity<List<Map.Entry<TipoServicoSoftware, Long>>> contarServicosNoMes(int ano, int mes) {
        // Calcular o início e o fim do mês
        LocalDateTime inicioMes = LocalDateTime.of(ano, mes, 1, 0, 0, 0, 0);
        LocalDateTime fimMes = inicioMes.plusMonths(1).minusSeconds(1);

        // Buscar todos os softwares no intervalo de data
        List<Software> softwaresNoMes = softwareRepository.findAllByDataSolicitacaoBetween(inicioMes, fimMes);

        // Verifica se existem registros no mês
        if (softwaresNoMes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        // Contar as ocorrências de cada serviço
        Map<TipoServicoSoftware, Long> contagemServicos = Arrays.stream(TipoServicoSoftware.values())
                .collect(Collectors.toMap(
                        tipoServico -> tipoServico,
                        tipoServico -> softwaresNoMes.stream()
                                .filter(software -> software.getServicos().contains(tipoServico))
                                .count()
                ));

        // Ordenar o mapa em ordem decrescente pelo valor (contagem)
        List<Map.Entry<TipoServicoSoftware, Long>> contagemOrdenada = contagemServicos.entrySet().stream()
                .sorted(Map.Entry.<TipoServicoSoftware, Long>comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(contagemOrdenada);
    }
}
