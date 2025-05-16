package br.com.eds.api.eds.service.arquivo;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.*;

@Service
public class ArquivoService {

    private final String IMPRESSAO_DIR = "src/main/resources/static/impressao_design/";
    private final String DESIGN_DIR = "src/main/resources/static/criacao_design/";
    private final String CONSERTO_DIR = "src/main/resources/static/conserto";

    public String salvarArquivo(MultipartFile file, String arquivoAtual, boolean isImpressao, boolean isConserto) throws IOException {
        if(file == null || file.isEmpty()){
            return null;
        }

        String diretorio = isImpressao ? IMPRESSAO_DIR : (isConserto ? CONSERTO_DIR : DESIGN_DIR);
        Files.createDirectories(Paths.get(diretorio));

        if (arquivoAtual != null && !arquivoAtual.isEmpty()){
            Path caminhoArquivoAtual = Paths.get(diretorio, arquivoAtual);
            Files.deleteIfExists(caminhoArquivoAtual);
        }

        String nomeArquivo = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path caminhoArquivo = Paths.get(diretorio,nomeArquivo);

        Files.copy(file.getInputStream(), caminhoArquivo, StandardCopyOption.REPLACE_EXISTING);

        String relativePath = (isImpressao ? "impressao_design/" : (isConserto ? "conserto/" : "criacao_design/")) + nomeArquivo;

        return "/download/" + relativePath;
    }

    public String getIMPRESSAO_DIR() {
        return IMPRESSAO_DIR;
    }

    public String getCONSERTO_DIR() {
        return CONSERTO_DIR;
    }

    public String getDESIGN_DIR() {
        return DESIGN_DIR;
    }
}