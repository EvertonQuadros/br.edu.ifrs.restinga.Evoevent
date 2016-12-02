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

/**
 * Classe modelo que define a entidade inscrições.
 * @author notrevequadrosc@gmail.com
 */
@Entity
@Table(name="inscricoes")
public class Inscricao extends Entidade implements Serializable{

    @Id
    @GeneratedValue
    private int id;
    
    @OneToOne
    @JoinColumn(name = "evento_id")
    private Evento evento;
    
    @OneToOne
    @JoinColumn(name = "perfil_id")
    private Perfil perfil;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    @Column(name = "data_inscricao")
    private Date dataInscricao;

    @Column(name="tipo_inscricao", columnDefinition="enum('ALUNO','PROFESSOR','ORIENTADOR','COLABORADOR','PALESTRANTE','REVISOR DE TRABALHOS','GERENTE DE EVENTO')")
    private String tipoInscricao;
    
    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public Date getDataInscricao() {
        return dataInscricao;
    }

    public void setDataInscricao(Date dataInscricao) {
        this.dataInscricao = dataInscricao;
    }

    public String getTipoInscricao() {
        return tipoInscricao;
    }

    public void setTipoInscricao(String tipoInscricao) {
        this.tipoInscricao = tipoInscricao;
    }
   
}
