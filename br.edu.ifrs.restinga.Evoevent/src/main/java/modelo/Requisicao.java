/*
 * Copyright 2016 notrevequadrosc@gmail.com.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import modelo.utils.DateUtil;

/**
 * Classe que operação requisições entre perfis ou entre perfis e o sistema.
 * @author notrevequadrosc@gmail.com
 */
@Entity
@Table(name="requisicoes")
public class Requisicao extends Entidade implements Serializable{

    /**
    * Tipo de enumeradores que representam as situações da requisição :
    * AGUARDANDO (Aguardando resposta),
    * RECUSADO (Respondido e recusado pelo usuário),
    * ACEITO (Respondido e aceito pelo usuário),
    * CANCELADO (Cancelado pelo usuário),
    * EXPIRADO (Não respondido até a data de vencimento).
    */
    public enum SituacaoEnum {
        AGUARDANDO,
        RECUSADO,
        ACEITO,
        CANCELADO,
        EXPIRADO;
    }
    
    @Id
    @GeneratedValue
    private int id;
    
    @OneToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    
    @OneToOne
    @JoinColumn(name = "perfil_id")
    private Perfil perfil;
    
    @OneToOne
    @JoinColumn(name = "destino_id")
    private Perfil destino;
    
    @Column(name="situacao", columnDefinition="enum('AGUARDANDO','RECUSADO','ACEITO','CANCELADO','EXPIRADO')")
    private String situacao;
    
    private boolean respondido;
    private String resposta;
    private String descricao;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    @Column(name = "data_vida")
    private Date dataVencimento;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    @Column(name = "data_resposta")
    private Date dataResposta;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public Perfil getDestino() {
        return destino;
    }

    public void setDestino(Perfil destino) {
        this.destino = destino;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(SituacaoEnum situacao) {
        this.situacao = situacao.toString();
    }

    public boolean isRespondido() {
        return respondido;
    }

    public void setRespondido() {
        this.respondido = true;
    }

    public String getResposta() {
        return resposta;
    }

    public void setResposta(String resposta) {
        this.resposta = resposta;
    }
    
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento() {
        this.dataVencimento = DateUtil.DateFunctions.getNovaData(
                DateUtil.UnidadeTempoEnum.DIA,
                7);
    }

    public Date getDataResposta() {
        return dataResposta;
    }

    public void setDataResposta(Date dataResposta) {
        this.dataResposta = dataResposta;
    }
    
}
