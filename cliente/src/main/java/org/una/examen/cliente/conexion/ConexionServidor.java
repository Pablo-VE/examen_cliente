/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.examen.cliente.conexion;

import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

/**
 *
 * @author Pablo-VE
 */
public class ConexionServidor {
    private Client client;
    private Invocation.Builder builder;
    private WebTarget webTarget;
    private Response response;
    private final String apiUrl = "http://localhost:";
    
    public ConexionServidor(int api, String direccion){
        this.client = ClientBuilder.newClient();
        setDireccion(api, direccion);
    }
    
    public ConexionServidor(int api, String direccion, String parametros, Map<String, Object> valores){
        String url="";
        if(api==1){
            url="8181/";
        }else{
            if(api==2){
                url="8097/";
            }else{
                if(api==3){
                    url="8089/";
                }
            }
        }

        this.client = ClientBuilder.newClient();
        this.webTarget = client.target(apiUrl + url + direccion).path(parametros).resolveTemplates(valores);
        this.builder = webTarget.request(MediaType.APPLICATION_JSON);
        MultivaluedMap<String, Object> headers = new MultivaluedHashMap<>();
        headers.add("Content-Type", "application/json; charset=UTF-8");
        headers.add("Accept", "application/json");
        builder.headers(headers);
    }
    
    private void setDireccion(int api, String direccion){
        String apiUrl="http://localhost:";
        if(api==1){
            apiUrl+=apiUrl+"8181/";
        }else{
            if(api==2){
                url="8097/";
            }else{
                if(api==3){
                    url="8089/";
                }
            }
        }
        
        
        this.webTarget = client.target(apiUrl + url + direccion);
        this.builder = webTarget.request(MediaType.APPLICATION_JSON);
        MultivaluedMap<String, Object> headers = new MultivaluedHashMap<>();
        headers.add("Content-Type", "application/json; charset=UTF-8");
        headers.add("Accept", "application/json");
        builder.headers(headers);
    }
    
    public void get() {
        response = builder.get();
    }
    
    public void post(Object clazz) {
        Entity<?> entity = Entity.entity(clazz, "application/json; charset=UTF-8");
        response = builder.post(entity);
    }

    public void put(Object clazz) {
        Entity<?> entity = Entity.entity(clazz, "application/json; charset=UTF-8");
        response = builder.put(entity);
    }

    public void delete() {
        response = builder.delete();
    }
    
    public int getStatus() {
        return response.getStatus();
    }
    
    public Boolean isError(){
        return (getStatus() != HttpServletResponse.SC_OK)&&(getStatus() != HttpServletResponse.SC_CREATED);
    }
  
    public String getError() {
        if (response.getStatus() != HttpServletResponse.SC_OK) {
            String mensaje;
            if (response.getMediaType().equals(MediaType.APPLICATION_JSON_TYPE)) {
                mensaje = response.readEntity(String.class);
            } else {
                mensaje = response.getStatusInfo().getReasonPhrase();
            }
            return mensaje;
        }
        return null;
    }
    
    public Object readEntity(Class clazz) {  
        return response.readEntity(clazz);
    }
    
    public Object readEntity(GenericType<?> genericType) {
        return response.readEntity(genericType);
    }
}
