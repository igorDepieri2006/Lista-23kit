package br.com.starlog.controller;

import br.com.starlog.model.CriptoMensagem;
import java.util.ArrayList;
import java.util.List;

/**
 * CAMADA CONTROLLER (O Maestro da Transmissão)
 * 
 * ATENÇÃO ESTUDANTE:
 * Implemente esta classe para orquestrar as requisições da View,
 * convertendo os tipos brutos e alimentando o Model!
 * 
 * REGRA DE ARQUITETURA:
 * O Controller manipula lógica e dados, NÃO componentes visuais (zero imports de javax.swing.*)!
 */
public class TransmissaoController {

    private final List<String> bufferTransmissoes = new ArrayList<>();

    /**
     * Orquestra a transmissão da mensagem.
     * 
     * @param remetenteRaw Remetente vindo do campo de texto da tela
     * @param msgRaw       Mensagem vinda do campo de texto da tela
     * @param chaveStr     Chave digitada como texto na tela
     * @return Pacote formatado no padrão: [REMETENTE] #CHECKSUM -> TEXTO_CIFRADO
     */
     public String processarTransmissao(String remetenteRaw, String msgRaw, String chaveStr) {
        // 1. Converter chaveStr para inteiro com tratamento de erro robusto
        int deslocamento;
        try {
            if (chaveStr == null) {
                throw new NumberFormatException();
            }
            deslocamento = Integer.parseInt(chaveStr.trim());
        } catch (NumberFormatException | NullPointerException ex) {
            throw new IllegalArgumentException("Chave deve ser um número inteiro válido.");
        }

        // 2. Instanciar CriptoMensagem passando os dados
        CriptoMensagem cripto = new CriptoMensagem(remetenteRaw, msgRaw, deslocamento);

        // 3. Obter o texto cifrado e o checksum do Model
        String textoCifrado = cripto.cifrar();
        int checksum = cripto.calcularChecksum();

        // 4. Formatar o pacote
        String pacoteFinal = String.format("[%s] #%d -> %s", cripto.getRemetente(), checksum, textoCifrado);

        // 5. Adicionar o pacote à lista bufferTransmissoes
        bufferTransmissoes.add(pacoteFinal);

        // 6. Retornar o pacote formatado
        return pacoteFinal;
    }

    public int getTotalTransmissoes() {
        return bufferTransmissoes.size();
    }
}