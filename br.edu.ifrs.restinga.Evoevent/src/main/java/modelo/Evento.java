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
 * Classe modelo que define a entidade evento.
 * @author notrevequadrosc@gmail.com
 */
@Entity
@Table(name="eventos")
public class Evento extends Entidade implements Serializable{
    
    @Id
    @GeneratedValue
    private int id;

    @Column(name = "nome_evento")
    private String nomeEvento;
    
    @OneToOne
    @JoinColumn(name = "estado_id")
    private Estado estado;
    
    @OneToOne
    @JoinColumn(name = "cidade_id")
    private Cidade cidade;
    
    @Column(name = "local_evento")
    private String localEvento;
    
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(name = "data_hora_inicio")
    private Date dataHoraInicio;
    
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(name = "data_hora_termino")
    private Date dataHoraTermino;
    
    private String area;
    private String descricao;
    
    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public String getNomeEvento() {
        return nomeEvento;
    }

    public void setNomeEvento(String nomeEvento) {
        this.nomeEvento = nomeEvento;
    }
    
    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public String getLocalEvento() {
        return localEvento;
    }

    public void setLocalEvento(String localEvento) {
        this.localEvento = localEvento;
    }

    public Date getDataHoraInicio() {
        return dataHoraInicio;
    }

    public void setDataHoraInicio(Date dataHoraInicio) {
        this.dataHoraInicio = dataHoraInicio;
    }

    public Date getDataHoraTermino() {
        return dataHoraTermino;
    }

    public void setDataHoraTermino(Date dataHoraTermino) {
        this.dataHoraTermino = dataHoraTermino;
    }

    public String[] getArea() {
        
        if(area != null){
            return area.split(", ");
        }
        
        return null;
        
    }

    public void setArea(String[] vetorArea) {
        
        StringBuilder areas = new StringBuilder();

        for (String areasObjeto : vetorArea) {
           areas.append(areasObjeto).append(", ");
        }
        
        area = areas.toString();
         
    }
    
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
}
