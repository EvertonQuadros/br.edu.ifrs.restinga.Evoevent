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
/**
 * Author:  notrevequadrosc@gmail.com
 * Created: 09/10/2016
 */

package modelo.utils;

/**
 * Hibernate Utility class with a convenient method to get Session Factory
 * object.
 *
 * @author notrevequadrosc@gmail.com
 */

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.hibernate.tool.hbm2ddl.SchemaExport;
 
public class HibernateUtil {
 
    /**
     * Método main que inicializa a configuração do nosso hibernateUtil.
     * @param args
     */
    public static void main(String[] args) {

        System.out.println("Inicio");

        Configuration cfg = new Configuration();
        cfg.configure();
        SchemaExport se = new SchemaExport(cfg);
        se.create(true, true);

        System.out.println("Fim");

    }

    private static SessionFactory sessionFactory = null;
    private static ServiceRegistry serviceRegistry;

    static {

        try {
            sessionFactory = getSessionFactory();
        } 
        catch (Throwable ex) {

            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);

        }

    }

    /**
     * Método que captura a SessionFactory criada na classe e retorna para a 
     * classe que está invocando o método.
     * @return Um objeto SessionFactory contendo os dados da sessão corrente.
     */
    public static SessionFactory getSessionFactory() {

        if (sessionFactory == null) {

            Configuration configuration = new Configuration();
            configuration.configure();
            serviceRegistry = new ServiceRegistryBuilder().applySettings(
                            configuration.getProperties()).build();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            sessionFactory.openSession();
            return sessionFactory;

        }

        return sessionFactory;

    }

    /**
     * Método que invoca getSessionFactory e retorna a sessão corrente, no mesmo
     * método.
     * @return Objeto sessão contendo a sessão corrente.
     */
    public static Session getSession() {
        return getSessionFactory().getCurrentSession();
    }
        
}