package br.com.eds.api.eds.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.*;
import br.com.eds.api.eds.repository.criacaoDesign.ArquivoCriacaoDesignRepository;
import br.com.eds.api.eds.repository.impressao.ArquivoImpressaoRepository;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class ArquivoService {

    @Autowired
    private ArquivoImpressaoRepository arquivoImpressaoRepository;

    @Autowired
    private ArquivoCriacaoDesignRepository arquivoCriacao;

    private static final String IMPRESSAO_DIR = "src/main/resources/static/impressao_design/";
    private static final String DESIGN_DIR = "src/main/resources/static/criacao_design/";

    public String salvarArquivo(MultipartFile file, boolean isImpressao) throws IOException {
        if(file == null || file.isEmpty()){
            return null;
        }

        String diretorio = isImpressao ? IMPRESSAO_DIR : DESIGN_DIR;
        Files.createDirectories(Paths.get(diretorio));

        String nomeArquivo = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path caminhoArquivo = Paths.get(diretorio,nomeArquivo);

        Files.copy(file.getInputStream(), caminhoArquivo, StandardCopyOption.REPLACE_EXISTING);

        return nomeArquivo;  // Retorna o caminho para salvar na entidade
    }

}