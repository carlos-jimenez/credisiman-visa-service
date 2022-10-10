package com.siman.credisiman.visa.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConsultaDatosClienteResponse {

    @JsonProperty("statusCode") @JsonAlias("statusCode")
    public String statusCode;

    @JsonProperty("status") @JsonAlias("status")
    public String status;

    @JsonProperty("statusMessage") @JsonAlias("statusMessage")
    public String statusMessage;

    @JsonProperty("nombre")  @JsonAlias("nombre")
    public String nombre;

    @JsonProperty("segundoNombre")  @JsonAlias("segundoNombre")
    public String segundoNombre;

    @JsonProperty("primerApellido")  @JsonAlias("primerApellido")
    public String primerApellido;

    @JsonProperty("segundoApellido") @JsonAlias("segundoApellido")
    public String segundoApellido;

    @JsonProperty("apellidoCasada")  @JsonAlias("apellidoCasada")
    public String apellidoCasada;

    @JsonProperty("tipoIdentificacion")  @JsonAlias("tipoIdentificacion")
    public String tipoIdentificacion;

    @JsonProperty("identificacion") @JsonAlias("identificacion")
    public String identificacion;

    @JsonProperty("correoElectronico") @JsonAlias("correoElectronico")
    public String correoElectronico;

    @JsonProperty("celular")  @JsonAlias("celular")
    public String celular;

    @JsonProperty("direccion")  @JsonAlias("direccion")
    public String direccion;

    @JsonProperty("direccionPatrono") @JsonAlias("direccionPatrono")
    public String direccionPatrono;

    //others
    public String code;
    public String message;
    public String tipoMensaje;
    public String tipoPersoneria;
    public String nacimiento;
    public String estadoCivil;
    public String sexo;
    public String codigoNacionalidad;
    public String telefono1;
    public String direccion2;
    public String direccion3;
    public String ciudad;
    public String estado;
    public String codigoPostal;
    public String apartado;
    public String codigoRegion;
    public String codigoZona;
    public String codigoSector;
    public String codigoProfesion;
    public String claseEnte;
    public String telefonoCelular;
    public String filler4;
    public String numeroCliente;
    public String vivienda;
    public String parienteCercano;
    public String parentesco;
    public String telefonoPariente;
    public String direccionPariente;
    public String ciudadPariente;
    public String codigoPostalPariente;
    public String apartadoPariente;
    public String nombrePatrono;
    public String codigoAreaPatrono;
    public String codigoActividadPatrono;
    public String telefonoPatrono;
    public String ciudadPatrono;
    public String estadoPatrono;
    public String codigoPostalPatron;
    public String apartadoPatrono;
    public String fechaIngresoTrabajo;
    public String puestoActual;
    public String sueldoActual;
    public String origenFondosPatrono;
    public String promedioIngresoPatrono;
    public String promedioEgresoPatrono;
    public String rangoSueldo;
    public String registroMercantil;
    public String fechaInicioEmpresa;
    public String representanteLegal;
    public String identidadRepLegal;
    public String correoElectronicoAdicional;
    public String envioEstado;
    public String numeroNIT;
    public String lugarEmisionNIT;
    public String categoriaCliente;
    public String tomo;
    public String folio;
    public String asiento;
    public String codigoArea;
    public String codigoActividad;
    public String codigoVendedor;
    public String origenFondos;
    public String promedioIngreso;
    public String promedioEgreso;
    public String nombreComercialEmp;
    public String tipoEntidad;
    public String fechaCreacion;
    public String lugarCreacion;
    public String representanteLegalAnterior;
    public String representanteLegalActual;
}