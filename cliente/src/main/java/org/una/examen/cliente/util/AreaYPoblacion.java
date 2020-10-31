/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.una.examen.cliente.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.una.examen.cliente.dto.CantonDTO;
import org.una.examen.cliente.dto.DistritoDTO;
import org.una.examen.cliente.dto.ProvinciaDTO;
import org.una.examen.cliente.dto.UnidadDTO;
import org.una.examen.cliente.service.CantonService;
import org.una.examen.cliente.service.DistritoService;
import org.una.examen.cliente.service.UnidadService;

/**
 *
 * @author Jeffry
 */
public class AreaYPoblacion {
    
    private int codigoProvincia;
    private int codigoCanton;
    private int codigoDistrito;
    private int codigoUnidad;
    
    private String cProvincia;
    private String cCanton;
    private String cDistrito;
    private String cUnidad;
    
    public Long getPoblacionProvincia(ProvinciaDTO provincia){
        Long cantidadPoblacion = Long.valueOf(0);
        CantonService cantonService = new CantonService();
        Respuesta res = cantonService.getByProvincia(provincia.getId());
        if(res.getEstado()){
            List<CantonDTO> cantones = new ArrayList<CantonDTO>();
            cantones = (List<CantonDTO>) res.getResultado("Cantones");
            if(cantones.size()>0){
                for(int i=0; i<cantones.size(); i++){
                    cantidadPoblacion+= getPoblacionCanton(cantones.get(i));
                }
            }
        }
        return cantidadPoblacion;
    }
    
    public Long getPoblacionCanton(CantonDTO canton){
        Long cantidadPoblacion = Long.valueOf(0);
        DistritoService distritoService = new DistritoService();
        Respuesta res = distritoService.getByCanton(canton.getId());
        if(res.getEstado()){
            List<DistritoDTO> distritos = new ArrayList<DistritoDTO>();
            distritos = (List<DistritoDTO>) res.getResultado("Distritos");
            if(distritos.size()>0){
                for(int i=0; i<distritos.size(); i++){
                    cantidadPoblacion+= getPoblacionDistrito(distritos.get(i));
                }
            }
        }
        return cantidadPoblacion;
    }
    
    public Long getPoblacionDistrito(DistritoDTO distrito){
        Long cantidadPoblacion = Long.valueOf(0);
        UnidadService unidadService = new UnidadService();
        Respuesta res = unidadService.getByDistrito(distrito.getId());
        if(res.getEstado()){
            List<UnidadDTO> unidades = new ArrayList<UnidadDTO>();
            unidades = (List<UnidadDTO>) res.getResultado("Unidades");
            if(unidades.size()>0){
                for(int i=0; i<unidades.size(); i++){
                    cantidadPoblacion+= unidades.get(i).getPoblacion();
                }
            }
        }
        return cantidadPoblacion;
    }
    
    public Long getAreaProvincia(ProvinciaDTO provincia){
        Long areaCuadrada = Long.valueOf(0);
        CantonService cantonService = new CantonService();
        Respuesta res = cantonService.getByProvincia(provincia.getId());
        if(res.getEstado()){
            List<CantonDTO> cantones = new ArrayList<CantonDTO>();
            cantones = (List<CantonDTO>) res.getResultado("Cantones");
            if(cantones.size()>0){
                for(int i=0; i<cantones.size(); i++){
                    areaCuadrada+= getAreaCanton(cantones.get(i));
                }
            }
        }
        return areaCuadrada;
    }
    
    public Long getAreaCanton(CantonDTO canton){
        Long areaCuadrada = Long.valueOf(0);
        DistritoService distritoService = new DistritoService();
        Respuesta res = distritoService.getByCanton(canton.getId());
        if(res.getEstado()){
            List<DistritoDTO> distritos = new ArrayList<DistritoDTO>();
            distritos = (List<DistritoDTO>) res.getResultado("Distritos");
            if(distritos.size()>0){
                for(int i=0; i<distritos.size(); i++){
                    areaCuadrada+= getAreaDistrito(distritos.get(i));
                }
            }
        }
        return areaCuadrada;
    }
    
    public Long getAreaDistrito(DistritoDTO distrito){
        Long areaCuadrada = Long.valueOf(0);
        UnidadService unidadService = new UnidadService();
        Respuesta res = unidadService.getByDistrito(distrito.getId());
        if(res.getEstado()){
            List<UnidadDTO> unidades = new ArrayList<UnidadDTO>();
            unidades = (List<UnidadDTO>) res.getResultado("Unidades");
            if(unidades.size()>0){
                for(int i=0; i<unidades.size(); i++){
                    areaCuadrada+= unidades.get(i).getArea();
                }
            }
        }
        return areaCuadrada;
    }
    
    public void setAreaPoblacionProvincias(ArrayList<ProvinciaDTO> provincias){
        for(int i=0; i<provincias.size(); i++){
            Long area = getAreaProvincia(provincias.get(i));
            provincias.get(i).setArea(area);
            Long poblacion = getPoblacionProvincia(provincias.get(i));
            provincias.get(i).setPoblacion(poblacion);
        }
    }
    
    public void setAreaPoblacionCantones(ArrayList<CantonDTO> cantones){
        for(int i=0; i<cantones.size(); i++){
            Long area = getAreaCanton(cantones.get(i));
            cantones.get(i).setArea(area);
            Long poblacion = getPoblacionCanton(cantones.get(i));
            cantones.get(i).setPoblacion(poblacion);
        }
    }
    
    public void setAreaPoblacionDistritos(ArrayList<DistritoDTO> distritos){
        for(int i=0; i<distritos.size(); i++){
            Long area = getAreaDistrito(distritos.get(i));
            distritos.get(i).setArea(area);
            Long poblacion = getPoblacionDistrito(distritos.get(i));
            distritos.get(i).setPoblacion(poblacion);
        }
    }
    
    public int getCodigoNuevoProvincia(ArrayList<ProvinciaDTO> provincias){
        int codigo = 0;
        if(provincias != null){
            for(int i=0; i<provincias.size(); i++){
                codigo = provincias.get(i).getCodigo();
            }
            codigoProvincia = codigo+1;
        }
        cProvincia = String.valueOf(codigoProvincia);
        return codigoProvincia;
    }
    
    public int getCodigoNuevoCanton(ArrayList<CantonDTO> cantones){
        int codigo = 0;
        if(cantones != null){
            for(int i=0; i<cantones.size(); i++){
            codigo = cantones.get(i).getCodigo();
            }
            codigoCanton = codigo+1;
            
        }
        cCanton = String.valueOf(codigoCanton);
        String c = cProvincia + cCanton;
        return codigoCanton;
    }
    
    public int getCodigoNuevoDistrito(ArrayList<DistritoDTO> distritos){
        int codigo = 0;
        if(distritos != null){
            for(int i=0; i<distritos.size(); i++){
            codigo = distritos.get(i).getCodigo();
            }
            codigoDistrito = codigo+1;
        }
        
        cDistrito = String.valueOf(codigoDistrito);
        String c = cCanton + cDistrito;
        
        return codigoDistrito;
    }
    
    public int getCodigoNuevoUnidad(ArrayList<UnidadDTO> unidades){
        int codigo = 0;
        if(unidades != null){
            for(int i=0; i<unidades.size(); i++){
            codigo = unidades.get(i).getCodigo();
            }
            codigoUnidad = codigo+1;
        }
        
        cUnidad = String.valueOf(codigoUnidad);
        String c = cCanton + cUnidad;
        
        return codigoUnidad;
    }
}
